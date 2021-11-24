package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.AcademicLevelEntity;
import com.anhnc2.ehealicords.data.entity.FolkEntity;
import com.anhnc2.ehealicords.data.entity.MaritalStatusEntity;
import com.anhnc2.ehealicords.data.entity.NationalityEntity;
import com.anhnc2.ehealicords.data.entity.OccupationEntity;
import com.anhnc2.ehealicords.data.entity.RelativeEntity;
import com.anhnc2.ehealicords.data.entity.ReligionEntity;

import java.util.List;

public interface MetaService {
    List<NationalityEntity> getNationalities();
    List<FolkEntity> getFolks();
    List<OccupationEntity> getOccupations();
    List<MaritalStatusEntity> getMaritalStatuses();
    List<ReligionEntity> getReligions();
    List<RelativeEntity> getRelatives();
    List<AcademicLevelEntity> getAcademicLevels();
}
