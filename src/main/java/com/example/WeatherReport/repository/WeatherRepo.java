package com.example.WeatherReport.repository;

import com.example.WeatherReport.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeatherRepo extends JpaRepository<Weather, UUID> {
    Weather getReferenceById(long l);
}
