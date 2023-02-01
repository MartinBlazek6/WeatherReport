package com.example.WheatherLadyTeamBartifact.repository;
import com.example.WheatherLadyTeamBartifact.model.City;
import com.example.WheatherLadyTeamBartifact.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RegionRepo extends JpaRepository <Region, UUID> {

    public Region findByName (String regionName);
    public Region getRegionByName(String name);

}
