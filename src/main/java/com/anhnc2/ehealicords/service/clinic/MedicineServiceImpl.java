package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.MedicineEntity;
import com.anhnc2.ehealicords.data.request.MedicineCreationRequest;
import com.anhnc2.ehealicords.data.response.MedicineResponse;
import com.anhnc2.ehealicords.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public List<MedicineResponse> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(MedicineResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public MedicineResponse createMedicine(MedicineCreationRequest request) {
        return new MedicineResponse(medicineRepository.save(request.toEntity()));
    }

    @Override
    public MedicineResponse getMedicine(Long medicineId) {
        return new MedicineResponse(medicineRepository.getById(medicineId));
    }

    @Override
    public MedicineResponse updateMedicine(Long medicineId, MedicineCreationRequest request) {
        MedicineEntity entity = medicineRepository.getById(medicineId);

        entity.setMedicineCode(request.getMedicineCode());
        entity.setMedicineName(request.getMedicineName());
        entity.setContent(request.getContent());
        entity.setDosageForms(request.getDosageForms());
        entity.setUsageForms(request.getUsageForms());
        entity.setMedicineType(request.getMedicineType());
        entity.setUnit(request.getUnit());
        entity.setUnitPrice(request.getUnitPrice());

        return new MedicineResponse(medicineRepository.saveAndFlush(entity));
    }

    @Override
    public void delete(Long medicineId) {
        medicineRepository.deleteById(medicineId);
    }
}
