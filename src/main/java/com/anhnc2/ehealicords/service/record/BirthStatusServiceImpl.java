package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.BirthStatusEntity;
import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.BirthStatusCreationRequest;
import com.anhnc2.ehealicords.data.response.BirthStatusDetailsResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.BirthStatusRepository;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BirthStatusServiceImpl implements BirthStatusService {

    private final BirthStatusRepository birthStatusRepository;
    private final ExHistoryRepository exHistoryRepository;
    private final SpecialistRepository specialistRepository;

    private final SpecialistService specialistService;

    @Override
    public List<BirthStatusDetailsResponse> getAllBirthStatus(Long patientId) {
        List<BirthStatusEntity> entities = birthStatusRepository.findAllByPatientId(patientId);

        List<BirthStatusDetailsResponse> responses = entities.stream()
                .map(BirthStatusDetailsResponse::new)
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
    public BirthStatusDetailsResponse createBirthStatus(BirthStatusCreationRequest request) {
        ExHistoryEntity ex = exHistoryRepository.findByPatientIdAndStatus(request.getPatientId(), "ĐANG HOÀN THÀNH");

        if (ex == null) {
            throw new AppException(StatusCode.NONE_RECORD_OPEN);
        }

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        BirthStatusEntity entity = request.toEntity();
        entity.setHistoryId(ex.getId());
        entity.setUpdatedDoctorId(sp.getId());

        BirthStatusDetailsResponse response = new BirthStatusDetailsResponse(birthStatusRepository.save(entity));

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public void delete(Long birthStatusId) {
        birthStatusRepository.deleteById(birthStatusId);
    }

    @Override
    public BirthStatusDetailsResponse getBirthStatus(Long birthStatusId) {
        BirthStatusEntity entity = birthStatusRepository.getById(birthStatusId);
        BirthStatusDetailsResponse response = new BirthStatusDetailsResponse(entity);

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(specialistRepository.getById(entity.getUpdatedDoctorId()).getFullName());

        return response;
    }

    @Override
    public BirthStatusDetailsResponse updateBirthStatus(Long birthStatusId, BirthStatusCreationRequest request) {
        BirthStatusEntity entity = birthStatusRepository.getById(birthStatusId);

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        entity.setUpdatedDoctorId(sp.getId());

        entity.setNormalBirth(request.getNormalBirth());
        entity.setHardBirth(request.getHardBirth());
        entity.setCaesareanSection(request.getCaesareanSection());
        entity.setBornPrematurely(request.getBornPrematurely());
        entity.setSuffocatedAtBirth(request.getSuffocatedAtBirth());
        entity.setBornWeight(request.getBornWeight());
        entity.setBornLength(request.getBornLength());
        entity.setBirthDefects(request.getBirthDefects());
        entity.setOthersProblem(request.getOthersProblem());

        BirthStatusDetailsResponse response = new BirthStatusDetailsResponse(birthStatusRepository.saveAndFlush(entity));

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }
}
