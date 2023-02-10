package com.example.WeatherReport.services;

import com.example.WeatherReport.model.City;
import com.example.WeatherReport.model.DTO.Temperatures;
import com.example.WeatherReport.model.DTO.UpdateCityDTO;
import com.example.WeatherReport.model.DTO.WeatherInfoByCityAndCountryCode;
import com.example.WeatherReport.model.Region;
import com.example.WeatherReport.model.Weather;
import com.example.WeatherReport.repository.CityRepo;
import com.example.WeatherReport.repository.RegionRepo;
import com.example.WeatherReport.repository.WeatherRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class CityService {
    private final String API_KEY = System.getenv("API_KEY");
    private final RegionRepo regionRepo;
    private final WeatherRepo weatherRepo;

    private final CityRepo cityRepository;

    public ResponseEntity addCity(String city) {
        city = city.replace("{\"string\":\"", "");
        city = city.replace("\"}", "");

        try {
            if (cityRepository.getCityByName(city).getName().equals(city)) {
                return new ResponseEntity<>("City " + city + " already exist", HttpStatus.METHOD_NOT_ALLOWED);
            }
        } catch (Exception ignored) {}
        cityRepository.save(new City(city));
        getCityWeather(new WeatherInfoByCityAndCountryCode(cityRepository.getCityByName(city),"SK"));
        return new ResponseEntity<>("City " + city + " added", HttpStatus.OK);


    }

    public void deleteEvidence(UUID id) {
        cityRepository.delete(cityRepository.getReferenceById(id));
    }

    public UUID getIdFromCityName(String cityName) {
        return cityRepository.getCityByName(cityName).getId();
    }

    public City getCityByCityName(String cityName) {
        return cityRepository.getCityByName(cityName);
    }

    public List<City> getAllCitiesByRegion(String regionName) {
        return regionRepo.findByName(regionName).getCities();
    }

    public ResponseEntity getCityWeather(WeatherInfoByCityAndCountryCode params) {
        if (getCityByCityName(params.getCity()).getWeather() == null || getCityByCityName(params.getCity()).getLastWeatherCall() < System.currentTimeMillis()) {
            RestTemplate restTemplate = new RestTemplate();
            var restTemplateForObject =
                    restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?q="
                            + params.getCity() + "," + params.getCountryCode().toUpperCase() + "&appid="
                            + API_KEY + "&units=metric", Temperatures.class);
            Weather weather =
                    new Weather(restTemplateForObject.getMain().getTemp_max(),
                            restTemplateForObject.getMain().getTemp_min(),
                            restTemplateForObject.getMain().getTemp(),
                            restTemplateForObject.getMain().getFeels_like(),
                            restTemplateForObject.getCoord().getLon(),
                            restTemplateForObject.getCoord().getLat());
            City city = getCityByCityName(params.getCity());
            city.setWeather(weather);
            city.setLastWeatherCall(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15));
            weatherRepo.save(weather);
            updateCity(city);
            log.warn("External API has been called");
        } else {
            log.info("Internal database has been called");
        }


        return new ResponseEntity<>(getCityByCityName(params.getCity()).getWeather(), HttpStatus.OK);

    }

    public ResponseEntity setRegion(UpdateCityDTO cityDTO) {
        boolean isCityUpdated = cityDTO.getNewN() != null;
        boolean isRegionUpdated = cityDTO.getReg() != null;
        boolean isPopulationUpdated = cityDTO.getPop() != null;
        String finalStatus;
        try {
            City city = getCityByCityName(cityDTO.getOld());
            String cityName = isCityUpdated && !Objects.equals(city.getName(), cityDTO.getNewN()) ? "City " + city.getName() + " renamed to " + cityDTO.getNewN() : "City name is " + cityDTO.getOld();
            String regionName = isRegionUpdated ? "City added to region  " + cityDTO.getReg() : city.getPopulation() == null ? "City is not in region yet " : "City is in " + city.getRegion().getName() + " region";
            String populationNumber = (isPopulationUpdated && !Objects.equals(city.getPopulation(), cityDTO.getPop())) ? "Population was " + city.getPopulation() + " now is " + cityDTO.getPop() : city.getPopulation() == null ? "Population is 0 citizens" : "Population is " + city.getPopulation() + " citizens";
            finalStatus = cityName + "\n" + regionName + "\n" + populationNumber;
            city.setName(isCityUpdated ? cityDTO.getNewN() : city.getName());
            updateCity(city);
            if (regionRepo.findByName(cityDTO.getReg()) == null) {
                Region region = new Region();
                region.setName(cityDTO.getReg());
                city.setRegion(isRegionUpdated ? region : city.getRegion());
                city.setPopulation(!isPopulationUpdated ? city.getPopulation() : cityDTO.getPop());
                regionRepo.save(region);
                updateCity(city);

            } else {
                Region region = regionRepo.getRegionByName(cityDTO.getReg());
                city.setRegion(isRegionUpdated ? region : city.getRegion());
                regionRepo.saveAndFlush(region);
                updateCity(city);
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>("City " + cityDTO.getOld() + " does not exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(finalStatus, HttpStatus.OK);
    }

    public void updateCity(City city) {
        cityRepository.saveAndFlush(city);
    }


}
