package com.example.WeatherReport.repository;
import com.example.WeatherReport.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepo extends JpaRepository <Region, UUID> {

    public Region findByName (String regionName);
    public Region getRegionByName(String name);

}
