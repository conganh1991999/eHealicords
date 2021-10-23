package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.data.entity.PatientEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.response.PatientResponse;
import com.anhnc2.ehealicords.repository.DistrictRepository;
import com.anhnc2.ehealicords.repository.PatientRepository;
import com.anhnc2.ehealicords.repository.ProvinceRepository;
import com.anhnc2.ehealicords.repository.WardRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final SpecialistService specialistService;

    private final PatientRepository patientRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @Override
    public PatientResponse createPatient(PatientCreationRequest patient) {
        SpecialistEntity thisSpecialist = specialistService.getCurrentSpecialist();

        PatientEntity patientEntity = patient.toEntity();
        patientEntity.setBranchId(thisSpecialist.getBranchId());
        patientEntity.setSpecialistId(thisSpecialist.getId());
        patientEntity.setPersonalHealthId(patientEntity.getIdentityCardNumber());
        patientEntity.setCreatedTime(System.currentTimeMillis());
        patientEntity.setUpdatedTime(System.currentTimeMillis());

        patientEntity = patientRepository.save(patientEntity);

        PatientResponse response = new PatientResponse(patientEntity);
        response.setBirthProvince(provinceRepository.getById(patientEntity.getBirthProvinceId()).toBuilder().build());
        response.setTempProvince(provinceRepository.getById(patientEntity.getTempProvinceId()).toBuilder().build());
        response.setTempDistrict(districtRepository.getById(patientEntity.getTempDistrictId()).toBuilder().build());
        response.setTempWard(wardRepository.getById(patientEntity.getTempWardId()).toBuilder().build());

        return response;
    }

}
