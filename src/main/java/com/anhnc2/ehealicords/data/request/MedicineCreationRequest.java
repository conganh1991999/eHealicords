package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.MedicineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicineCreationRequest {
    private String medicineCode;
    private String medicineName;
    private String content;
    private String dosageForms;
    private String usageForms;
    private String medicineType;
    private String unit;
    private Double unitPrice;

    public MedicineEntity toEntity() {
        return MedicineEntity.builder()
                .medicineCode(medicineCode)
                .medicineName(medicineName)
                .content(content)
                .dosageForms(dosageForms)
                .usageForms(usageForms)
                .medicineType(medicineType)
                .unit(unit)
                .unitPrice(unitPrice)
                .build();
    }
}
