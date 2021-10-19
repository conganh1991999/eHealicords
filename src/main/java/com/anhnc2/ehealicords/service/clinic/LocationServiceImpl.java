package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;
import com.anhnc2.ehealicords.repository.DistrictRepository;
import com.anhnc2.ehealicords.repository.ProvinceRepository;
import com.anhnc2.ehealicords.repository.WardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @Override
    public List<ProvinceEntity> getProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public List<DistrictEntity> getDistricts(Integer provinceId) {
        return districtRepository.findAllByProvinceId(provinceId);
    }

    @Override
    public List<WardEntity> getWards(Integer districtId) {
        return wardRepository.findAllByDistrictId(districtId);
    }

    @Override
    public Optional<ProvinceEntity> getProvinceById(Integer id) {
        return provinceRepository.findById(id);
    }

    @Override
    public Optional<DistrictEntity> getDistrictById(Integer id) {
        return districtRepository.findById(id);
    }

    @Override
    public Optional<WardEntity> getWardById(Integer id) {
        return wardRepository.findById(id);
    }

}
