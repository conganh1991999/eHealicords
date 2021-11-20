package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExHistoryCreationRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String recordType;
    private Long patientId;

    public ExHistoryEntity toEntity() {
        return ExHistoryEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .recordType(recordType)
                .patientId(patientId)
                .build();
    }
}
