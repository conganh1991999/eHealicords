package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import com.anhnc2.ehealicords.data.entity.PrescriptionEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.PrescriptionCreationRequest;
import com.anhnc2.ehealicords.data.response.PrescriptionResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.ExHistoryRepository;
import com.anhnc2.ehealicords.repository.PrescriptionRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import com.anhnc2.ehealicords.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private static final String PRESCRIPTION_KEY_PREFIX = "prescriptions";

    private final PrescriptionRepository prescriptionRepository;
    private final ExHistoryRepository exHistoryRepository;
    private final SpecialistRepository specialistRepository;

    private final SpecialistService specialistService;
    private final StorageService storageService;

    @Override
    public PrescriptionResponse creatPrescription(PrescriptionCreationRequest request) {
        ExHistoryEntity ex = exHistoryRepository.findByPatientIdAndStatus(request.getPatientId(), "ĐANG HOÀN THÀNH");

        if (ex == null) {
            throw new AppException(StatusCode.NONE_RECORD_OPEN);
        }

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        PrescriptionEntity entity = request.toEntity();

        entity.setHistoryId(ex.getId());
        entity.setUpdatedSpecialistId(sp.getId());

        entity.setPrescriptionStatus("ĐANG HOÀN THÀNH");

        PrescriptionResponse response = new PrescriptionResponse(prescriptionRepository.save(entity));

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());

        response.setUpdatedSpecialistName(specialistRepository.getById(response.getUpdatedSpecialistId()).getFullName());
        response.setPerformedSpecialistName(
                response.getPerformedSpecialistId() == null ? null : specialistRepository.getById(response.getPerformedSpecialistId()).getFullName()
        );
        response.setSuppliedSpecialistName(
                response.getSuppliedSpecialistId() == null ? null : specialistRepository.getById(response.getSuppliedSpecialistId()).getFullName()
        );

        return response;
    }

    @Override
    public List<PrescriptionResponse> getPrescriptions(Long patientId) {
        List<PrescriptionEntity> entities = prescriptionRepository.findAllByPatientId(patientId);

        List<PrescriptionResponse> responses = entities.stream()
                .map(PrescriptionResponse::new)
                .collect(Collectors.toList());

        responses.forEach(e -> {
            ExHistoryEntity ex = exHistoryRepository.getById(e.getHistoryId());
            e.setStartDate(ex.getStartDate());
            e.setEndDate(ex.getEndDate());
            e.setStatus(ex.getStatus());
            e.setUpdatedSpecialistName(specialistRepository.getById(e.getUpdatedSpecialistId()).getFullName());
            e.setPerformedSpecialistName(
                    e.getPerformedSpecialistId() == null ? null : specialistRepository.getById(e.getPerformedSpecialistId()).getFullName()
            );
            e.setSuppliedSpecialistName(
                    e.getSuppliedSpecialistId() == null ? null : specialistRepository.getById(e.getSuppliedSpecialistId()).getFullName()
            );
        });

        return responses;
    }

    @Override
    public PrescriptionResponse getPrescription(Long presId) {
        PrescriptionEntity entity = prescriptionRepository.getById(presId);
        PrescriptionResponse response = new PrescriptionResponse(entity);

        ExHistoryEntity ex = exHistoryRepository.getById(entity.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());

        response.setUpdatedSpecialistName(specialistRepository.getById(entity.getUpdatedSpecialistId()).getFullName());
        response.setPerformedSpecialistName(
                response.getPerformedSpecialistId() == null ? null : specialistRepository.getById(entity.getPerformedSpecialistId()).getFullName()
        );
        response.setSuppliedSpecialistName(
                response.getSuppliedSpecialistId() == null ? null : specialistRepository.getById(entity.getSuppliedSpecialistId()).getFullName()
        );

        return response;
    }

    @Override
    public PrescriptionResponse updatePrescription(Long presId, PrescriptionCreationRequest request) {
        PrescriptionEntity entity = prescriptionRepository.getById(presId);

        SpecialistEntity sp = specialistService.getCurrentSpecialist();

        entity.setUpdatedSpecialistId(sp.getId());
        entity.setPerformedSpecialistId(request.getPerformedSpecialistId());
        entity.setSuppliedSpecialistId(request.getSuppliedSpecialistId());
        entity.setContent(request.getContent());

        PrescriptionResponse response = new PrescriptionResponse(prescriptionRepository.saveAndFlush(entity));

        ExHistoryEntity ex = exHistoryRepository.getById(response.getHistoryId());

        response.setStartDate(ex.getStartDate());
        response.setEndDate(ex.getEndDate());
        response.setStatus(ex.getStatus());

        response.setUpdatedSpecialistName(specialistRepository.getById(response.getUpdatedSpecialistId()).getFullName());
        response.setPerformedSpecialistName(
                response.getPerformedSpecialistId() == null ? null : specialistRepository.getById(response.getPerformedSpecialistId()).getFullName()
        );
        response.setSuppliedSpecialistName(
                response.getSuppliedSpecialistId() == null ? null : specialistRepository.getById(response.getSuppliedSpecialistId()).getFullName()
        );

        return response;
    }

    @Override
    public String savePrescription(Long presId, MultipartFile briefFile) {
        PrescriptionEntity prescription = prescriptionRepository.getById(presId);

        String oldKey = prescription.getBriefFileUrl();

        String newKey = briefFile == null ? null : saveBriefFile(prescription.getPatientId(), briefFile);

        prescription.setBriefFileUrl(newKey);
        prescription.setPrescriptionStatus("HOÀN THÀNH");
        prescriptionRepository.save(prescription);

        if (oldKey != null) {
            storageService.delete(oldKey);
        }

        return newKey == null ? "null" : newKey;
    }

    private String saveBriefFile(Long patientId, MultipartFile briefFile) {
        String filename = briefFile.getOriginalFilename();
        String avatarKey =
                String.join(
                        "/",
                        PRESCRIPTION_KEY_PREFIX,
                        "patient", patientId.toString(),
                        FileUtil.appendCurrentTimeMillisToName(
                                filename == null ? PRESCRIPTION_KEY_PREFIX : filename
                        )
                );

        try {
            storageService.put(avatarKey, FileUtil.convertMultipartFileToFile(briefFile));
            return avatarKey;
        } catch (IOException e) {
            throw new RegisterException(StatusCode.FILE_SAVED_FAIL);
        }
    }

    @Override
    public String getPrescriptionBrief(Long presId) {
        String avtKey = prescriptionRepository.getById(presId).getBriefFileUrl();
        return avtKey == null ? "null" : avtKey;
    }

    @Override
    public void deletePrescription(Long presId) {
        String avtKey = prescriptionRepository.getById(presId).getBriefFileUrl();

        if (avtKey != null) {
            storageService.delete(avtKey);
        }

        prescriptionRepository.deleteById(presId);
    }
}
