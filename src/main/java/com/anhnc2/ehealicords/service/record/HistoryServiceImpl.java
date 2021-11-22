package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.ClinicalEntity;
import com.anhnc2.ehealicords.data.entity.DiagAndConclusionEntity;
import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.PrescriptionEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.entity.SubclinicalEntity;
import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.request.ExHistoryUpdateRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryBriefResponse;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.ClinicalRepository;
import com.anhnc2.ehealicords.repository.DiagAndConclusionRepository;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.PatientRepository;
import com.anhnc2.ehealicords.repository.PrescriptionRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.repository.SubclinicalRepository;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import com.anhnc2.ehealicords.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.anhnc2.ehealicords.constant.StatusCode.PATIENT_INCOMPLETE_RECORD;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private static final String EX_HISTORY_KEY_PREFIX = "examination_histories";

    private final SpecialistService specialistService;

    private final ExHistoryRepository exHistoryRepository;
    private final BranchRepository branchRepository;
    private final SpecialistRepository specialistRepository;
    private final ClinicalRepository clinicalRepository;
    private final SubclinicalRepository subclinicalRepository;
    private final DiagAndConclusionRepository diagAndConclusionRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;

    private final StorageService storageService;

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

    @Override
    public ExHistoryBriefResponse briefExaminationHistory(Long patientId, Long historyId) {
        ExHistoryBriefResponse response = new ExHistoryBriefResponse();

        ClinicalEntity clinicalEntity = clinicalRepository.findByPatientIdAndHistoryId(patientId, historyId);
        response.setPathologicalProgress(clinicalEntity == null ? "Chưa ghi nhận" : clinicalEntity.getPathologicalProgress());
        response.setPulse(clinicalEntity == null ? 0.0 : clinicalEntity.getPulse());
        response.setTemperature(clinicalEntity == null ? 0.0 : clinicalEntity.getTemperature());
        response.setBp(clinicalEntity == null ? "0/0" : clinicalEntity.getBp());
        response.setBreathing(clinicalEntity == null ? 0.0 : clinicalEntity.getBreathing());
        response.setWeight(clinicalEntity == null ? 0.0 : clinicalEntity.getWeight());

        DiagAndConclusionEntity diagAndConclusionEntity = diagAndConclusionRepository.findByPatientIdAndHistoryId(patientId, historyId);
        response.setMainDisease(diagAndConclusionEntity == null ? "Chưa ghi nhận" : diagAndConclusionEntity.getMainDisease());
        response.setAdditionalDisease(diagAndConclusionEntity == null ? "Chưa ghi nhận" : diagAndConclusionEntity.getAdditionalDisease());
        response.setSolution(diagAndConclusionEntity == null ? "Chưa ghi nhận" : diagAndConclusionEntity.getSolution());
        response.setDiseaseConclusion(diagAndConclusionEntity == null ? "Chưa ghi nhận" : diagAndConclusionEntity.getDiseaseConclusion());
        response.setConsultation(diagAndConclusionEntity == null ? "Chưa ghi nhận" : diagAndConclusionEntity.getConsultation());

        List<SubclinicalEntity> subclinicalEntities = subclinicalRepository.findAllByPatientIdAndHistoryId(patientId, historyId);
        if (subclinicalEntities != null) {
            for (SubclinicalEntity entity : subclinicalEntities) {
                response.setSubclinicalBriefs("");
            }
        }

        List<PrescriptionEntity> prescriptionEntities = prescriptionRepository.findAllByPatientIdAndHistoryId(patientId, historyId);
        response.setNoPres(prescriptionEntities.size());
        response.setTotal(
                response.getNoXq() + response.getNoCt() + response.getNoSa() + response.getNoXn() + response.getNoOt() + response.getNoPres()
        );

        ExHistoryEntity ex = exHistoryRepository.getById(historyId);
        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setRecordType(ex.getRecordType());
        response.setStatus(ex.getStatus());
        response.setBriefFileUrl(ex.getBriefFileUrl());

        response.setPatientName(patientRepository.getById(ex.getPatientId()).getFullName());
        response.setBranchName(branchRepository.getById(ex.getBranchId()).getName());
        response.setExDoctorName(specialistRepository.getById(ex.getExDoctorId()).getFullName());
        response.setReDoctorName(specialistRepository.getById(ex.getReDoctorId()).getFullName());

        return response;
    }

    @Override
    public String saveExaminationHistory(Long historyId, MultipartFile briefFile) {
        ExHistoryEntity exHistoryEntity = exHistoryRepository.getById(historyId);

        String oldKey = exHistoryEntity.getBriefFileUrl();

        String newKey = briefFile == null ? null : saveBriefFile(exHistoryEntity.getPatientId(), briefFile);

        exHistoryEntity.setBriefFileUrl(newKey);
        exHistoryEntity.setStatus("HOÀN THÀNH");
        exHistoryRepository.save(exHistoryEntity);

        if (oldKey != null) {
            storageService.delete(oldKey);
        }

        return newKey == null ? "null" : newKey;
    }

    private String saveBriefFile(Long patientId, MultipartFile briefFile) {
        String filename = briefFile.getOriginalFilename();
        String fileKey =
                String.join(
                        "/",
                        EX_HISTORY_KEY_PREFIX,
                        "patient", patientId.toString(),
                        FileUtil.appendCurrentTimeMillisToName(
                                filename == null ? EX_HISTORY_KEY_PREFIX : filename
                        )
                );

        try {
            storageService.put(fileKey, FileUtil.convertMultipartFileToFile(briefFile));
            return fileKey;
        } catch (IOException e) {
            throw new RegisterException(StatusCode.FILE_SAVED_FAIL);
        }
    }

    @Override
    public String getExaminationHistoryBrief(Long patientId, Long historyId) {
        String fileKey = exHistoryRepository.getById(historyId).getBriefFileUrl();
        return fileKey == null ? "null" : fileKey;
    }

    @Override
    public void deleteExaminationHistory(Long patientId, Long historyId) {

    }
}
