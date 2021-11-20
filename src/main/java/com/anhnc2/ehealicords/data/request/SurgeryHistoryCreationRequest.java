package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.SurgeryHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurgeryHistoryCreationRequest {
    private Long patientId;
    private String surgicalSystem;
    private String yearOfSurgery;
    private String description;
    private String whereOfSurgery;

    public SurgeryHistoryEntity toEntity() {
        return SurgeryHistoryEntity.builder()
                .patientId(patientId)
                .surgicalSystem(surgicalSystem)
                .yearOfSurgery(yearOfSurgery)
                .description(description)
                .whereOfSurgery(whereOfSurgery)
                .build();
    }
}
