package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.clinic.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/protected/locations")
@AllArgsConstructor
public class LocationApi {

    private final LocationService locationService;

    @GetMapping("/provinces")
    // @Cacheable(cacheNames = "provinces")
    public HttpResponse<List<ProvinceEntity>> getProvinceList() {
        return HttpResponseImpl.<List<ProvinceEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(locationService.getProvinces())
                .build();
    }

    @GetMapping("/provinces/{provinceId}/districts")
    // @Cacheable(cacheNames = "districts")
    public HttpResponse<List<DistrictEntity>> getDistrictList(@PathVariable(name = "provinceId") Integer provinceId) {
        return HttpResponseImpl.<List<DistrictEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(locationService.getDistricts(provinceId))
                .build();
    }

    @GetMapping("/districts/{districtId}/wards")
    // @Cacheable("wards")
    public HttpResponse<List<WardEntity>> getWardList(@PathVariable(name = "districtId") Integer districtId) {
        return HttpResponseImpl.<List<WardEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(locationService.getWards(districtId))
                .build();
    }
}
