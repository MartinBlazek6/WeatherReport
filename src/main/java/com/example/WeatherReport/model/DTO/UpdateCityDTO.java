package com.example.WeatherReport.model.DTO;

import lombok.Data;
//add population

@Data
public class UpdateCityDTO {
    private String old;
    private String newN;
    private String reg;
    private Long pop;

}
