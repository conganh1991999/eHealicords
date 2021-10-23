package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.data.entity.PatientEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.request.PatientUpdateRequest;
import com.anhnc2.ehealicords.data.response.PatientDetailsResponse;
import com.anhnc2.ehealicords.data.response.PatientResponse;
import com.anhnc2.ehealicords.repository.DistrictRepository;
import com.anhnc2.ehealicords.repository.PatientRepository;
import com.anhnc2.ehealicords.repository.ProvinceRepository;
import com.anhnc2.ehealicords.repository.WardRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<PatientResponse> getAllPatients() {
        List<PatientEntity> patientEntities
                = patientRepository.findAllBySpecialistId(specialistService.getCurrentSpecialist().getId());

        return patientEntities.stream()
                .map(p -> {
                    PatientResponse response = new PatientResponse(p);
                    response.setBirthProvince(p.getBirthProvinceEntity().toBuilder().build());
                    response.setTempProvince(p.getTempProvinceEntity().toBuilder().build());
                    response.setTempDistrict(p.getTempDistrictEntity().toBuilder().build());
                    response.setTempWard(p.getTempWardEntity().toBuilder().build());
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    public PatientDetailsResponse getPatientInformation(Long patientId) {
        return new PatientDetailsResponse(patientRepository.getById(patientId));
    }

    @Override
    public PatientDetailsResponse updatePatientPhase2(Long patientId, PatientUpdateRequest request) {
        PatientEntity patient = patientRepository.getById(patientId);

        patient.setIdentityCardIssuePlace(request.getIdentityCardIssuePlace());
        patient.setIdentityCardIssueDate(request.getIdentityCardIssueDate());
        patient.setTemporaryResidenceAddress(request.getTemporaryResidenceAddress());
        patient.setPermanentAddress(request.getPermanentAddress());
        patient.setNationality(request.getNationality());
        patient.setReligion(request.getReligion());
        patient.setFolk(request.getFolk());
        patient.setOccupation(request.getOccupation());
        patient.setAcademicLevel(request.getAcademicLevel());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setUpdatedTime(System.currentTimeMillis());
        patient.setPermProvinceId(request.getPermProvinceId());
        patient.setPermDistrictId(request.getPermDistrictId());
        patient.setPermWardId(request.getPermWardId());

        patient = patientRepository.saveAndFlush(patient);

        PatientDetailsResponse detailsResponse = new PatientDetailsResponse(patient);
        detailsResponse.setPermProvince(provinceRepository.getById(patient.getPermProvinceId()).toBuilder().build());
        detailsResponse.setPermDistrict(districtRepository.getById(patient.getPermDistrictId()).toBuilder().build());
        detailsResponse.setPermWard(wardRepository.getById(patient.getPermWardId()).toBuilder().build());

        return detailsResponse;
    }

}
