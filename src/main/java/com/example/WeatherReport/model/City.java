package com.example.WeatherReport.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;
    private String name;
    @JsonIgnore
    private Long lastWeatherCall;
    private Long population;
    @OneToOne(cascade = CascadeType.ALL)
    private Weather weather;


    public City(String name, Long population) {
        this.name = name;
        this.population = population;
    }

    public City(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "regionId", foreignKey = @ForeignKey(name = "fk_city_region_id"))
    @JsonIgnore
    private Region region;
}
