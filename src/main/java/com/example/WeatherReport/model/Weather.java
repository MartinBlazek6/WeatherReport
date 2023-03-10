package com.example.WeatherReport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    private Double maxTemp;
    private Double minTemp;
    private Double temp;
    private Double feelsLike;
    private Double longitude;
    private Double latitude;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private City city;

    public Weather(Double maxTemp, Double minTemp, Double temp,Double feelsLike, Double longitude, Double latitude) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
