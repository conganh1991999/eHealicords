package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.request.MedicineCreationRequest;
import com.anhnc2.ehealicords.data.response.MedicineResponse;

import java.util.List;

public interface MedicineService {
    List<MedicineResponse> getAllMedicines();
    MedicineResponse createMedicine(MedicineCreationRequest request);
    MedicineResponse getMedicine(Long medicineId);
    MedicineResponse updateMedicine(Long medicineId, MedicineCreationRequest request);
    void delete(Long medicineId);
}
