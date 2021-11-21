package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.MedicineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicineResponse {
    private Long id;
    private String medicineCode;
    private String medicineName;
    private String content;
    private String dosageForms;
    private String usageForms;
    private String medicineType;
    private String unit;
    private Double unitPrice;

    public MedicineResponse(MedicineEntity entity) {
        this.id = entity.getId();
        this.medicineCode = entity.getMedicineCode();
        this.medicineName = entity.getMedicineName();
        this.content = entity.getContent();
        this.dosageForms = entity.getDosageForms();
        this.usageForms = entity.getUsageForms();
        this.medicineType = entity.getMedicineType();
        this.unit = entity.getUnit();
        this.unitPrice = entity.getUnitPrice();
    }
}
