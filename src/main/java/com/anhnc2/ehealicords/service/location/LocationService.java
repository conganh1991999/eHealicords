package com.anhnc2.ehealicords.service.location;

import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<ProvinceEntity> getProvinces();

    List<DistrictEntity> getDistricts(int provinceId);

    List<WardEntity> getWards(int districtId);

    Optional<ProvinceEntity> getProvinceById(int id);

    Optional<DistrictEntity> getDistrictById(int id);

    Optional<WardEntity> getWardById(int id);
}
