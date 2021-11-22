package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.ClinicalEntity;
import com.anhnc2.ehealicords.data.entity.SubclinicalEntity;
import com.anhnc2.ehealicords.data.request.SubclinicalCreationRequest;
import com.anhnc2.ehealicords.data.response.ClinicalDetailsResponse;
import com.anhnc2.ehealicords.data.response.SubclinicalResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.SubclinicalRepository;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SubclinicalServiceImpl implements SubclinicalService {

    private static final String SUBCLINICAL_PREFIX = "subclinical";

    private final SubclinicalRepository subclinicalRepository;

    private final StorageService storageService;

    @Override
    public SubclinicalResponse getSubclinicalDetails(Long historyId, Long patientId) {
        SubclinicalEntity entity = subclinicalRepository.findByPatientIdAndHistoryId(patientId, historyId);

        if (entity == null) {
            throw new AppException(StatusCode.SUBCLINICAL_DETAILS_NOT_FOUND);
        }

        return new SubclinicalResponse(entity);
    }

    @Override
    public SubclinicalResponse createSubclinicalDetails(SubclinicalCreationRequest request) {
        SubclinicalEntity entity = new SubclinicalEntity();

        entity.setPatientId(request.getPatientId());
        entity.setHistoryId(request.getHistoryId());
        entity.setSubclinicalTypeId(request.getSubclinicalTypeId());
        entity.setSubclinicalBrief(request.getSubclinicalBrief());

        String briefFileKey = saveFile(request.getPatientId(), request.getHistoryId(), request.getBriefFile());
        entity.setBriefFileUrl(briefFileKey);

        List<MultipartFile> images = new ArrayList<>();
        images.add(request.getFile1());
        images.add(request.getFile2());
        images.add(request.getFile3());
        images.add(request.getFile4());
        images.add(request.getFile5());
        images.add(request.getFile6());
        images.add(request.getFile7());
        images.add(request.getFile8());

        List<String> imageKeys = new ArrayList<>();
        for (MultipartFile file : images) {
            if (file != null) {
                imageKeys.add(saveFile(request.getPatientId(), request.getHistoryId(), file));
            }
        }

        entity.setListImageKeys(String.join(",", imageKeys));

        SubclinicalEntity newEntity = subclinicalRepository.saveAndFlush(entity);

        return new SubclinicalResponse(newEntity);
    }

    private String saveFile(Long patientId, Long historyId, MultipartFile briefFile) {
        String filename = briefFile.getOriginalFilename();
        String avatarKey =
                String.join(
                        "/",
                        SUBCLINICAL_PREFIX,
                        "patient", patientId.toString(),
                        "history", historyId.toString(),
                        FileUtil.appendCurrentTimeMillisToName(
                                filename == null ? SUBCLINICAL_PREFIX : filename
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
    public void delete(Long subclinicalDetailsId) {
        SubclinicalEntity entity = subclinicalRepository.getById(subclinicalDetailsId);

        if (entity.getListImageKeys() != null) {
            final String[] split = entity.getListImageKeys().split(",");
            for (String s : split) {
                storageService.delete(s);
            }
        }
        if (entity.getBriefFileUrl() != null) {
            storageService.delete(entity.getBriefFileUrl());
        }
        subclinicalRepository.deleteById(subclinicalDetailsId);
    }

}
