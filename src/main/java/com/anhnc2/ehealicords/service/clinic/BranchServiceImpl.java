package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.constant.BranchStatus;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.BusinessHoursEntity;
import com.anhnc2.ehealicords.data.request.BranchCreationRequest;
import com.anhnc2.ehealicords.exception.BranchException;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.BusinessHoursRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;
    private final BusinessHoursRepository businessHoursRepository;

    @Override
    public void createBranch(BranchCreationRequest branchRequest) {
        BusinessHoursEntity businessHours =
                BusinessHoursEntity.builder()
                        .morningOpen(branchRequest.getMorningOpen())
                        .morningClose(branchRequest.getMorningClose())
                        .afternoonOpen(branchRequest.getAfternoonOpen())
                        .afternoonClose(branchRequest.getAfternoonClose())
                        .eveningOpen(branchRequest.getEveningOpen())
                        .eveningClose(branchRequest.getEveningClose())
                        .days(
                                branchRequest.getDays().stream()
                                        .map(day -> DayOfWeek.of(day).name())
                                        .collect(Collectors.joining(","))
                        )
                        .build();

        businessHoursRepository.saveAndFlush(businessHours);

        BranchEntity branch =
                BranchEntity.builder()
                        .name(branchRequest.getName())
                        .fullAddress(branchRequest.getFullAddress())
                        .address(branchRequest.getAddress())
                        .email(branchRequest.getEmail())
                        .phoneNumber(branchRequest.getPhoneNumber())
                        .provinceId(branchRequest.getProvinceId())
                        .districtId(branchRequest.getDistrictId())
                        .wardId(branchRequest.getWardId())
                        .status(BranchStatus.ACTIVE)
                        .businessHoursId(businessHours.getId())
                        .build();

        LOGGER.debug("Create BranchEntity: {}", branch);

        branchRepository.save(branch);
    }

    @Override
    public List<BranchEntity> getAllBranch() {
        return branchRepository.findAll();
    }

    @Override
    public BranchEntity getBranchById(Integer id) {
        return branchRepository
                .findById(id)
                .orElseThrow(() -> new BranchException(StatusCode.BRANCH_DOES_NOT_EXISTED));
    }

    @Override
    @Transactional
    public void updateBranch(BranchCreationRequest branch) {
        LOGGER.debug("Update BranchEntity: {}", branch);
        BranchEntity branchDAOCurrent =
                branchRepository
                        .findById(branch.getId())
                        .orElseThrow(() -> new BranchException(StatusCode.BRANCH_DOES_NOT_EXISTED));

        BusinessHoursEntity businessHourOfBranch = branchDAOCurrent.getBusinessHoursEntity();

        // Update branch information
        branchDAOCurrent.setName(branch.getName());
        branchDAOCurrent.setProvinceId(branch.getProvinceId());
        branchDAOCurrent.setDistrictId(branch.getDistrictId());
        branchDAOCurrent.setWardId(branch.getWardId());
        branchDAOCurrent.setAddress(branch.getAddress());
        branchDAOCurrent.setFullAddress(branch.getFullAddress());

        // Update business hours
        businessHourOfBranch.setMorningOpen(branch.getMorningOpen());
        businessHourOfBranch.setMorningClose(branch.getMorningClose());
        businessHourOfBranch.setAfternoonOpen(branch.getAfternoonOpen());
        businessHourOfBranch.setAfternoonClose(branch.getAfternoonClose());
        businessHourOfBranch.setEveningOpen(branch.getEveningOpen());
        businessHourOfBranch.setEveningClose(branch.getEveningClose());
        businessHourOfBranch.setDays(
                branch.getDays().stream()
                        .map(i -> DayOfWeek.of(i).name())
                        .collect(Collectors.joining(",")));

        branchRepository.saveAndFlush(branchDAOCurrent);
    }

}