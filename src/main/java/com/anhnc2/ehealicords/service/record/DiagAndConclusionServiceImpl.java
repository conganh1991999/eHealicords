package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.entity.DiagAndConclusionEntity;
import com.anhnc2.ehealicords.data.request.DiagAndConclusionRequest;
import com.anhnc2.ehealicords.data.response.DiagAndConclusionDetailsResponse;
import com.anhnc2.ehealicords.repository.DiagAndConclusionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiagAndConclusionServiceImpl implements DiagAndConclusionService {

    private final DiagAndConclusionRepository diagAndConclusionRepository;

    @Override
    public DiagAndConclusionDetailsResponse getDiagAndConclusion(Long historyId, Long patientId) {
        DiagAndConclusionEntity entity = diagAndConclusionRepository.findByPatientIdAndHistoryId(patientId, historyId);

        if (entity == null) {
            return new DiagAndConclusionDetailsResponse(new DiagAndConclusionEntity());
        }

        return new DiagAndConclusionDetailsResponse(entity);
    }

    @Override
    public DiagAndConclusionDetailsResponse createOrUpdateDiagAndConclusion(DiagAndConclusionRequest request) {
        DiagAndConclusionEntity entity;

        if (request.getId() == null) {
            entity = request.toEntity();
            diagAndConclusionRepository.save(entity);
        } else {
            entity = diagAndConclusionRepository.getById(request.getId());

            entity.setPatientId(request.getPatientId());
            entity.setHistoryId(request.getHistoryId());
            entity.setMainDisease(request.getMainDisease());
            entity.setAdditionalDisease(request.getAdditionalDisease());
            entity.setDiseasePrognosis(request.getDiseasePrognosis());
            entity.setSolution(request.getSolution());
            entity.setDiseaseConclusion(request.getDiseaseConclusion());
            entity.setConsultation(request.getConsultation());
            entity.setBriefFileUrl(request.getBriefFileUrl());
        }

        return new DiagAndConclusionDetailsResponse(diagAndConclusionRepository.save(entity));
    }

    @Override
    public void delete(Long dacId) {
        diagAndConclusionRepository.deleteById(dacId);
    }
}
