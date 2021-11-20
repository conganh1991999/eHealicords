package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.RiskFactorsEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.RiskFactorsCreationRequest;
import com.anhnc2.ehealicords.data.response.RiskFactorsDetailsResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.RiskFactorsRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RiskFactorsServiceImpl implements RiskFactorsService {

    private final RiskFactorsRepository riskFactorsRepository;
    private final ExHistoryRepository exHistoryRepository;
    private final SpecialistRepository specialistRepository;

    private final SpecialistService specialistService;

    @Override
    public List<RiskFactorsDetailsResponse> getAllRiskFactors(Long patientId) {
        List<RiskFactorsEntity> entities = riskFactorsRepository.findAllByPatientId(patientId);

        List<RiskFactorsDetailsResponse> responses = entities.stream()
                .map(RiskFactorsDetailsResponse::new)
                .collect(Collectors.toList());

        responses.forEach(e -> {
            ExHistoryEntity ex = exHistoryRepository.getById(e.getHistoryId());
            e.setStartDate(ex.getStartDate());
            e.setEndDate(ex.getEndDate());
            e.setStatus(ex.getStatus());
            e.setUpdatedDoctorName(specialistRepository.getById(e.getUpdatedDoctorId()).getFullName());
        });

        return responses;
    }

    @Override
    public RiskFactorsDetailsResponse createRiskFactors(RiskFactorsCreationRequest request) {
        ExHistoryEntity ex = exHistoryRepository.findByPatientIdAndStatus(request.getPatientId(), "ĐANG HOÀN THÀNH");

        if (ex == null) {
            throw new AppException(StatusCode.NONE_RECORD_OPEN);
        }

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        RiskFactorsEntity entity = request.toEntity();
        entity.setHistoryId(ex.getId());
        entity.setUpdatedDoctorId(sp.getId());

        RiskFactorsDetailsResponse response = new RiskFactorsDetailsResponse(riskFactorsRepository.save(entity));

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public RiskFactorsDetailsResponse getRiskFactors(Long riskFactorsId) {
        RiskFactorsEntity entity = riskFactorsRepository.getById(riskFactorsId);
        RiskFactorsDetailsResponse response = new RiskFactorsDetailsResponse(entity);

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(specialistRepository.getById(entity.getUpdatedDoctorId()).getFullName());

        return response;
    }

    @Override
    public RiskFactorsDetailsResponse updateRiskFactors(Long riskFactorsId, RiskFactorsCreationRequest request) {
        RiskFactorsEntity entity = riskFactorsRepository.getById(riskFactorsId);

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        entity.setUpdatedDoctorId(sp.getId());

        entity.setSmoke(request.getSmoke());
        entity.setDrink(request.getDrink());
        entity.setDrug(request.getDrug());
        entity.setExercise(request.getExercise());
        entity.setExposureFactors(request.getExposureFactors());
        entity.setOccupationalHazards(request.getOccupationalHazards());
        entity.setOthersHazards(request.getOthersHazards());

        RiskFactorsDetailsResponse response = new RiskFactorsDetailsResponse(riskFactorsRepository.saveAndFlush(entity));

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public void delete(Long riskFactorsId) {
        riskFactorsRepository.deleteById(riskFactorsId);
    }
}
