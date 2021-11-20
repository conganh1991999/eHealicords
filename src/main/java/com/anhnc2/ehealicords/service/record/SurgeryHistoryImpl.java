package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.entity.SurgeryHistoryEntity;
import com.anhnc2.ehealicords.data.request.SurgeryHistoryCreationRequest;
import com.anhnc2.ehealicords.data.response.SurgeryHistoryResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.repository.SurgeryHistoryRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurgeryHistoryImpl implements SurgeryHistoryService {

    private final SurgeryHistoryRepository surgeryHistoryRepository;
    private final ExHistoryRepository exHistoryRepository;
    private final SpecialistRepository specialistRepository;

    private final SpecialistService specialistService;

    @Override
    public List<SurgeryHistoryResponse> getAllSurgeryHistory(Long patientId) {
        List<SurgeryHistoryEntity> entities = surgeryHistoryRepository.findAllByPatientId(patientId);

        List<SurgeryHistoryResponse> responses = entities.stream()
                .map(SurgeryHistoryResponse::new)
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
    public SurgeryHistoryResponse createSurgeryHistory(SurgeryHistoryCreationRequest request) {
        ExHistoryEntity ex = exHistoryRepository.findByPatientIdAndStatus(request.getPatientId(), "ĐANG HOÀN THÀNH");

        if (ex == null) {
            throw new AppException(StatusCode.NONE_RECORD_OPEN);
        }

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        SurgeryHistoryEntity entity = request.toEntity();
        entity.setHistoryId(ex.getId());
        entity.setUpdatedDoctorId(sp.getId());

        SurgeryHistoryResponse response = new SurgeryHistoryResponse(surgeryHistoryRepository.save(entity));

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public SurgeryHistoryResponse getSurgeryHistory(Long surgeryHistoryId) {
        SurgeryHistoryEntity entity = surgeryHistoryRepository.getById(surgeryHistoryId);
        SurgeryHistoryResponse response = new SurgeryHistoryResponse(entity);

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(specialistRepository.getById(entity.getUpdatedDoctorId()).getFullName());

        return response;
    }

    @Override
    public SurgeryHistoryResponse updateSurgeryHistory(Long surgeryHistoryId, SurgeryHistoryCreationRequest request) {
        SurgeryHistoryEntity entity = surgeryHistoryRepository.getById(surgeryHistoryId);

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        entity.setUpdatedDoctorId(sp.getId());

        entity.setSurgicalSystem(request.getSurgicalSystem());
        entity.setYearOfSurgery(request.getYearOfSurgery());
        entity.setDescription(request.getDescription());
        entity.setWhereOfSurgery(request.getWhereOfSurgery());

        SurgeryHistoryResponse response = new SurgeryHistoryResponse(surgeryHistoryRepository.saveAndFlush(entity));

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());
        response.setUpdatedDoctorName(sp.getFullName());

        return response;
    }

    @Override
    public void delete(Long surgeryHistoryId) {
        surgeryHistoryRepository.deleteById(surgeryHistoryId);
    }
}
