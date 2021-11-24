package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.AcademicLevelEntity;
import com.anhnc2.ehealicords.data.entity.FolkEntity;
import com.anhnc2.ehealicords.data.entity.MaritalStatusEntity;
import com.anhnc2.ehealicords.data.entity.NationalityEntity;
import com.anhnc2.ehealicords.data.entity.OccupationEntity;
import com.anhnc2.ehealicords.data.entity.RelativeEntity;
import com.anhnc2.ehealicords.data.entity.ReligionEntity;
import com.anhnc2.ehealicords.repository.AcademicLevelRepository;
import com.anhnc2.ehealicords.repository.FolkRepository;
import com.anhnc2.ehealicords.repository.MaritalStatusRepository;
import com.anhnc2.ehealicords.repository.NationalityRepository;
import com.anhnc2.ehealicords.repository.OccupationRepository;
import com.anhnc2.ehealicords.repository.RelativeRepository;
import com.anhnc2.ehealicords.repository.ReligionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MetaServiceImpl implements MetaService {

    private final AcademicLevelRepository academicLevelRepository;
    private final FolkRepository folkRepository;
    private final MaritalStatusRepository maritalStatusRepository;
    private final NationalityRepository nationalityRepository;
    private final OccupationRepository occupationRepository;
    private final RelativeRepository relativeRepository;
    private final ReligionRepository religionRepository;

    @Override
    public List<NationalityEntity> getNationalities() {
        return nationalityRepository.findAll();
    }

    @Override
    public List<FolkEntity> getFolks() {
        return folkRepository.findAll();
    }

    @Override
    public List<OccupationEntity> getOccupations() {
        return occupationRepository.findAll();
    }

    @Override
    public List<MaritalStatusEntity> getMaritalStatuses() {
        return maritalStatusRepository.findAll();
    }

    @Override
    public List<ReligionEntity> getReligions() {
        return religionRepository.findAll();
    }

    @Override
    public List<RelativeEntity> getRelatives() {
        return relativeRepository.findAll();
    }

    @Override
    public List<AcademicLevelEntity> getAcademicLevels() {
        return academicLevelRepository.findAll();
    }

}
