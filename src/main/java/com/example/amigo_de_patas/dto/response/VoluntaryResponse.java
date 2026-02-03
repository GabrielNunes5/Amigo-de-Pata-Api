package com.example.amigo_de_patas.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoluntaryResponse {

    private UUID voluntaryId;
    private String voluntaryName;
    private String voluntaryEmail;
    private String voluntaryPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate voluntaryBirthDate;
    private String voluntaryOccupation;
    private String voluntaryInterestArea;
    private String voluntaryAvailability;
    private String voluntaryExperience;
    private String voluntaryMotivation;
    private Boolean hasTransportation;
    private Boolean hasRescueExperience;
    private Boolean canWorkOnWeekends;
    private String voluntarySpecialSkills;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
