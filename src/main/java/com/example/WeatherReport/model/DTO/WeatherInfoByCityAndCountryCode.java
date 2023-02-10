package com.example.WeatherReport.model.DTO;

import com.example.WeatherReport.model.City;
import lombok.Data;
//add population

@Data
public class WeatherInfoByCityAndCountryCode {
    private String city;
    private String countryCode; //SK CZ etc

    public WeatherInfoByCityAndCountryCode(City city, String countryCode) {
        this.city = city.getName();
        this.countryCode = countryCode;
    }
}
