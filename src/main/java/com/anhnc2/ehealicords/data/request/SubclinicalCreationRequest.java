package com.anhnc2.ehealicords.data.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SubclinicalCreationRequest {
    private Long patientId;
    private Long historyId;
    private Long subclinicalTypeId;
    private String subclinicalBrief;
    private MultipartFile briefFile;
    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;
    private MultipartFile file4;
    private MultipartFile file5;
    private MultipartFile file6;
    private MultipartFile file7;
    private MultipartFile file8;
}
