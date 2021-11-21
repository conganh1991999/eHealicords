package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.request.ExHistoryUpdateRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.anhnc2.ehealicords.constant.StatusCode.PATIENT_INCOMPLETE_RECORD;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final SpecialistService specialistService;

    private final ExHistoryRepository exHistoryRepository;
    private final BranchRepository branchRepository;
    private final SpecialistRepository specialistRepository;

    @Override
    public ExHistoryResponse creatExaminationHistory(ExHistoryCreationRequest request) {
        List<ExHistoryEntity> exHistoryEntities
                = exHistoryRepository.findAllByPatientIdAndStatus(request.getPatientId(), "ĐANG HOÀN THÀNH");

        if (!exHistoryEntities.isEmpty()) {
            throw new AppException(PATIENT_INCOMPLETE_RECORD);
        }

        SpecialistEntity thisSpecialist = specialistService.getCurrentSpecialist();
        ExHistoryEntity entity = request.toEntity();
        entity.setReDoctorId(thisSpecialist.getId());
        entity.setStatus("ĐANG HOÀN THÀNH");

        return new ExHistoryResponse(exHistoryRepository.save(entity));
    }

    @Override
    public List<ExHistoryResponse> getExaminationHistoriesOfPatient(Long patientId) {
        List<ExHistoryEntity> entities = exHistoryRepository.findAllByPatientId(patientId);
        return entities.stream().map(e -> {
            ExHistoryResponse exHistoryResponse = new ExHistoryResponse(e);
            exHistoryResponse.setBranchName(
                    e.getBranchId() == null ? null : branchRepository.getById(e.getBranchId()).getName()
            );
            exHistoryResponse.setExDoctorName(
                    e.getExDoctorId() == null ? null : specialistRepository.getById(e.getExDoctorId()).getFullName()
            );
            exHistoryResponse.setReDoctorName(specialistRepository.getById(e.getReDoctorId()).getFullName());
            return exHistoryResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public ExHistoryResponse getExaminationHistoryOfPatient(Long patientId, Long historyId) {
        ExHistoryEntity entity = exHistoryRepository.findByIdAndPatientId(historyId, patientId);
        ExHistoryResponse response = new ExHistoryResponse(entity);

        response.setBranchName(
                entity.getBranchId() == null ? null : branchRepository.getById(entity.getBranchId()).getName()
        );
        response.setExDoctorName(
                entity.getExDoctorId() == null ? null : specialistRepository.getById(entity.getExDoctorId()).getFullName()
        );
        response.setReDoctorName(specialistRepository.getById(entity.getReDoctorId()).getFullName());
        return response;
    }

    @Override
    public ExHistoryResponse updateExaminationHistory(Long historyId, ExHistoryUpdateRequest request) {
        ExHistoryEntity entity = exHistoryRepository.getById(historyId);

        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setRecordType(request.getRecordType());
        entity.setBranchId(request.getBranchId());
        entity.setExDoctorId(request.getExDoctorId());

        ExHistoryResponse response = new ExHistoryResponse(exHistoryRepository.saveAndFlush(entity));

        response.setBranchName(
                response.getBranchId() == null ? null : branchRepository.getById(response.getBranchId()).getName()
        );
        response.setExDoctorName(
                response.getExDoctorId() == null ? null : specialistRepository.getById(response.getExDoctorId()).getFullName()
        );
        response.setReDoctorName(specialistRepository.getById(response.getReDoctorId()).getFullName());

        return response;
    }
}
