package com.example.WeatherReport.controller;

import com.example.WeatherReport.model.City;
import com.example.WeatherReport.model.DTO.WeatherInfoByCityAndCountryCode;
import com.example.WeatherReport.model.DTO.UpdateCityDTO;
import com.example.WeatherReport.services.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class Controller {

    private final CityService cityService;

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody String cityName) {

   return cityService.addCity(cityName);
    }

    @GetMapping("/get")
    public ResponseEntity getAll(@RequestBody String regionName) {
        return new ResponseEntity<>(cityService.getAllCitiesByRegion(regionName), HttpStatus.OK);
    }

    @GetMapping("/get/region/{regionName}")
    public ResponseEntity getAllByVariable(@PathVariable String regionName) {
            try { // Looping it like this will I will avoid throwing ConcurrentModificationException
                IntStream.range(0,cityService.getAllCitiesByRegion(regionName).size())
                        .forEach(i->cityService.getCityWeather(new WeatherInfoByCityAndCountryCode(cityService.getAllCitiesByRegion(regionName).get(i),"SK")));
            } catch (Exception e) {
           log.error(String.valueOf(e));
                log.warn("Region not found");
                return new ResponseEntity<>("Region not found", HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<>(cityService.getAllCitiesByRegion(regionName), HttpStatus.OK);
    }

    @GetMapping("/get/city/{cityName}")
    public ResponseEntity getByVariable(@PathVariable String cityName) {
        try {
          cityService.getCityWeather(new WeatherInfoByCityAndCountryCode(cityService.getCityByCityName(cityName),"SK"));
            return new ResponseEntity<>(cityService.getCityByCityName(cityName), HttpStatus.OK);
        } catch (Exception e) {
//            log.error(String.valueOf(e));
//            log.warn("City not found");
            return new ResponseEntity<>("Region not found", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/weather")
    public ResponseEntity getCityWeather(@RequestBody WeatherInfoByCityAndCountryCode params) {
        return cityService.getCityWeather(params);
    }

    @PostMapping("/del")
    public ResponseEntity delete(@RequestBody String cityName) {
        try {
            cityService.deleteEvidence(cityService.getIdFromCityName(cityName));
        } catch (Exception e) {
            return new ResponseEntity<>("City not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("City deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody UpdateCityDTO cityDTO) {
        return cityService.setRegion(cityDTO);
    }


}
