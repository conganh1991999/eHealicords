package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.AcademicLevelEntity;
import com.anhnc2.ehealicords.data.entity.FolkEntity;
import com.anhnc2.ehealicords.data.entity.MaritalStatusEntity;
import com.anhnc2.ehealicords.data.entity.NationalityEntity;
import com.anhnc2.ehealicords.data.entity.OccupationEntity;
import com.anhnc2.ehealicords.data.entity.RelativeEntity;
import com.anhnc2.ehealicords.data.entity.ReligionEntity;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.clinic.MetaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/protected/meta")
@AllArgsConstructor
public class MetaApi {

    private final MetaService metaService;

    @GetMapping("/nationalities")
    public HttpResponse<List<NationalityEntity>> getNationalities() {
        return HttpResponseImpl.<List<NationalityEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getNationalities())
                .build();
    }

    @GetMapping("/folks")
    public HttpResponse<List<FolkEntity>> getFolks() {
        return HttpResponseImpl.<List<FolkEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getFolks())
                .build();
    }

    @GetMapping("/religions")
    public HttpResponse<List<ReligionEntity>> getReligions() {
        return HttpResponseImpl.<List<ReligionEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getReligions())
                .build();
    }

    @GetMapping("/occupations")
    public HttpResponse<List<OccupationEntity>> getOccupations() {
        return HttpResponseImpl.<List<OccupationEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getOccupations())
                .build();
    }

    @GetMapping("/academic-levels")
    public HttpResponse<List<AcademicLevelEntity>> getAcademicLevels() {
        return HttpResponseImpl.<List<AcademicLevelEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getAcademicLevels())
                .build();
    }

    @GetMapping("/marital-statuses")
    public HttpResponse<List<MaritalStatusEntity>> getMaritalStatuses() {
        return HttpResponseImpl.<List<MaritalStatusEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getMaritalStatuses())
                .build();
    }

    @GetMapping("/relatives")
    public HttpResponse<List<RelativeEntity>> getRelatives() {
        return HttpResponseImpl.<List<RelativeEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(metaService.getRelatives())
                .build();
    }

}
