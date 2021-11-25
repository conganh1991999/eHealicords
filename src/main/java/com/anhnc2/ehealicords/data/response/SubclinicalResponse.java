package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.SubclinicalEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubclinicalResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private Long subclinicalTypeId;
    private String subclinicalTypeName;
    private String subclinicalBrief;
    private String listImageKeys;
    private String briefFileUrl;

    public SubclinicalResponse(SubclinicalEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.subclinicalTypeId = entity.getSubclinicalTypeId();
        this.subclinicalBrief = entity.getSubclinicalBrief();
        this.listImageKeys = entity.getListImageKeys();
        this.briefFileUrl = entity.getBriefFileUrl();
    }
}
