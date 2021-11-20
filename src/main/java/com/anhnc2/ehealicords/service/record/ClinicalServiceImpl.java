package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.ClinicalEntity;
import com.anhnc2.ehealicords.data.request.ClinicalDetailsRequest;
import com.anhnc2.ehealicords.data.response.ClinicalDetailsResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.ClinicalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClinicalServiceImpl implements ClinicalService {

    private final ClinicalRepository clinicalRepository;

    @Override
    public ClinicalDetailsResponse getClinicalDetails(Long historyId, Long patientId) {
        ClinicalEntity entity = clinicalRepository.findByPatientIdAndHistoryId(patientId, historyId);

        if (entity == null) {
            throw new AppException(StatusCode.CLINICAL_DETAILS_NOT_FOUND);
        }

        return new ClinicalDetailsResponse(entity);
    }

    @Override
    public ClinicalDetailsResponse createOrUpdateClinicalDetails(ClinicalDetailsRequest request) {
        ClinicalEntity entity;

        if (request.getId() == null) {
            entity = request.toEntity();
            clinicalRepository.save(entity);
        } else {
            entity = clinicalRepository.getById(request.getId());

            entity.setPatientId(request.getPatientId());
            entity.setHistoryId(request.getHistoryId());
            entity.setReason(request.getReason());
            entity.setPathologicalProgress(request.getPathologicalProgress());
            entity.setPulse(request.getPulse());
            entity.setTemperature(request.getTemperature());
            entity.setBp(request.getBp());
            entity.setBreathing(request.getBreathing());
            entity.setHeight(request.getHeight());
            entity.setWeight(request.getWeight());
            entity.setFullBodyExamination(request.getFullBodyExamination());
            entity.setCirculatorySystem(request.getCirculatorySystem());
            entity.setRespiratorySystem(request.getRespiratorySystem());
            entity.setDigestiveSystem(request.getDigestiveSystem());
            entity.setGenitourinarySystem(request.getGenitourinarySystem());
            entity.setNerveSystem(request.getNerveSystem());
            entity.setMusculoskeletalSystem(request.getMusculoskeletalSystem());
            entity.setEntSystem(request.getEntSystem());
            entity.setMaxillofacialSystem(request.getMaxillofacialSystem());
            entity.setEye(request.getEye());
            entity.setNutritionalAndEndocrinologyEtc(request.getNutritionalAndEndocrinologyEtc());
            entity.setBriefFileUrl(request.getBriefFileUrl());
        }

        return new ClinicalDetailsResponse(clinicalRepository.save(entity));
    }

    @Override
    public void delete(Long clinicalDetailsId) {
        clinicalRepository.deleteById(clinicalDetailsId);
    }
}