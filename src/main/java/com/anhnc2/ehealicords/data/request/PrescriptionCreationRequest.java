package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.PrescriptionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrescriptionCreationRequest {
    private Long patientId;
    private Long performedSpecialistId;
    private Long suppliedSpecialistId;
    private String content;

    public PrescriptionEntity toEntity() {
        return PrescriptionEntity.builder()
                .patientId(patientId)
                .performedSpecialistId(performedSpecialistId)
                .suppliedSpecialistId(suppliedSpecialistId)
                .content(content)
                .build();
    }
}
