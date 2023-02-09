package com.example.WeatherReport.repository;
import com.example.WeatherReport.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepo extends JpaRepository <City, UUID> {
     City getCityByName (String cityName);
}
