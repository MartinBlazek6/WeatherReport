package com.example.WeatherReport.model.DTO;

import lombok.Data;
//add population

@Data
public class WeatherInfoByCityAndCountryCode {
    private String city;
    private String countryCode; //SK CZ etc
}
