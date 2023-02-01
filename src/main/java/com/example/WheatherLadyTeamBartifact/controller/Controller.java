package com.example.WheatherLadyTeamBartifact.controller;

import com.example.WheatherLadyTeamBartifact.model.City;
import com.example.WheatherLadyTeamBartifact.model.DTO.Temperatures;
import com.example.WheatherLadyTeamBartifact.model.DTO.WeatherInfoByCityAndCountryCode;
import com.example.WheatherLadyTeamBartifact.model.Region;
import com.example.WheatherLadyTeamBartifact.model.DTO.UpdateCityDTO;
import com.example.WheatherLadyTeamBartifact.model.Weather;
import com.example.WheatherLadyTeamBartifact.repository.CityRepo;
import com.example.WheatherLadyTeamBartifact.repository.RegionRepo;
import com.example.WheatherLadyTeamBartifact.repository.WeatherRepo;
import com.example.WheatherLadyTeamBartifact.services.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static java.rmi.server.LogStream.log;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final CityService cityService;
    private final String API_KEY = System.getenv("API_KEY");
    private final RegionRepo regionRepo;
    private final CityRepo cityRepo;
    private final WeatherRepo weatherRepo;

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody String cityName) {
        cityService.addCity(cityName);
        return new ResponseEntity<>("City added", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity getAll(@RequestBody String cityName) {
        cityService.gettAllCitiesByRegion(cityName);
        return new ResponseEntity<>(cityService.gettAllCitiesByRegion(cityName), HttpStatus.OK);
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
