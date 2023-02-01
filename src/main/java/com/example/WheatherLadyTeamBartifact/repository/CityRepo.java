package com.example.WheatherLadyTeamBartifact.repository;
import com.example.WheatherLadyTeamBartifact.model.City;
import com.example.WheatherLadyTeamBartifact.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepo extends JpaRepository <City, UUID> {
    public City getCityByName (String cityName);
}
