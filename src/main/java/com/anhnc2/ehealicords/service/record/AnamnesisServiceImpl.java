package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.AnamnesisEntity;
import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.AnamnesisCreationRequest;
import com.anhnc2.ehealicords.data.response.AnamnesisResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.AnamnesisRepository;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnamnesisServiceImpl implements AnamnesisService {

    private final AnamnesisRepository anamnesisRepository;
    private final ExHistoryRepository exHistoryRepository;
    private final SpecialistRepository specialistRepository;

    private final SpecialistService specialistService;

    @Override
    public List<AnamnesisResponse> getAllAnamnesis(Long patientId, String anamnesisType) {
        List<AnamnesisEntity> entities = anamnesisRepository.findAllByPatientIdAndAnamnesisType(patientId, anamnesisType);

        List<AnamnesisResponse> responses = entities.stream()
                .map(AnamnesisResponse::new)
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
    public AnamnesisResponse createAnamnesis(AnamnesisCreationRequest request) {
        ExHistoryEntity ex = exHistoryRepository.findByPatientIdAndStatus(request.getPatientId(), "ĐANG HOÀN THÀNH");

        if (ex == null) {
            throw new AppException(StatusCode.NONE_RECORD_OPEN);
        }

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        AnamnesisEntity entity = request.toEntity();
        entity.setHistoryId(ex.getId());
        entity.setUpdatedDoctorId(sp.getId());

        AnamnesisResponse response = new AnamnesisResponse(anamnesisRepository.save(entity));

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public AnamnesisResponse getAnamnesis(String anamnesisType, Long anamnesisId) {
        AnamnesisEntity entity = anamnesisRepository.findByIdAndAnamnesisType(anamnesisId, anamnesisType);
        AnamnesisResponse response = new AnamnesisResponse(entity);

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(specialistRepository.getById(entity.getUpdatedDoctorId()).getFullName());

        return response;
    }

    @Override
    public AnamnesisResponse updateAnamnesis(Long anamnesisId, AnamnesisCreationRequest request) {
        AnamnesisEntity entity = anamnesisRepository.getById(anamnesisId);

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        entity.setUpdatedDoctorId(sp.getId());

        entity.setName(request.getName());
        entity.setWho(request.getWho());
        entity.setDescription(request.getDescription());

        AnamnesisResponse response = new AnamnesisResponse(anamnesisRepository.saveAndFlush(entity));

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public void delete(String anamnesisType, Long anamnesisId) {
        anamnesisRepository.deleteById(anamnesisId);
    }
}
