package com.example.WheatherLadyTeamBartifact.repository;

import com.example.WheatherLadyTeamBartifact.model.City;
import com.example.WheatherLadyTeamBartifact.model.Region;
import com.example.WheatherLadyTeamBartifact.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeatherRepo extends JpaRepository<Weather, UUID> {
    Weather getReferenceById(long l);
}
