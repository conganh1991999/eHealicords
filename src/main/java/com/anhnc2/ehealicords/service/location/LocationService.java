package com.anhnc2.ehealicords.service.location;

import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<ProvinceEntity> getProvinces();

    List<DistrictEntity> getDistricts(Integer provinceId);

    List<WardEntity> getWards(Integer districtId);

    Optional<ProvinceEntity> getProvinceById(Integer id);

    Optional<DistrictEntity> getDistrictById(Integer id);

    Optional<WardEntity> getWardById(Integer id);
}
