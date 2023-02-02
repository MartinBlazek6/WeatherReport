package com.example.WheatherLadyTeamBartifact.model.DTO;

import jakarta.persistence.Lob;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
//add population

@Data
public class UpdateCityDTO {
    private String old;
    private String newN;
    private String reg;
    private Long pop;

}
