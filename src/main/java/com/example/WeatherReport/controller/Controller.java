package com.example.WeatherReport.controller;

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

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class Controller {

    private final CityService cityService;

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody String cityName) {
        cityService.addCity(cityName);
        return new ResponseEntity<>("City added", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity getAll(@RequestBody String regionName) {
        return new ResponseEntity<>(cityService.getAllCitiesByRegion(regionName), HttpStatus.OK);
    }

    @GetMapping("/get/{regionName}")
    public ResponseEntity getAllByVariable(@PathVariable String regionName) {
        return new ResponseEntity<>(cityService.getAllCitiesByRegion(regionName), HttpStatus.OK);
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
