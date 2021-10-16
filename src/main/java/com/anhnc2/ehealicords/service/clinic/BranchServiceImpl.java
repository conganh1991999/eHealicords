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
    public void updateBranch(BranchCreationRequest branchRequest) {
        BranchEntity current =
                branchRepository
                        .findById(branchRequest.getId())
                        .orElseThrow(() -> new BranchException(StatusCode.BRANCH_DOES_NOT_EXISTED));

        BusinessHoursEntity businessHoursOfBranch = current.getBusinessHoursEntity();

        // Update branch information
        current.setName(branchRequest.getName());
        current.setEmail(branchRequest.getEmail());
        current.setPhoneNumber(branchRequest.getPhoneNumber());
        current.setAddress(branchRequest.getAddress());
        current.setProvinceId(branchRequest.getProvinceId());
        current.setDistrictId(branchRequest.getDistrictId());
        current.setWardId(branchRequest.getWardId());
        current.setFullAddress(branchRequest.getFullAddress());

        // Update business hours
        businessHoursOfBranch.setMorningOpen(branchRequest.getMorningOpen());
        businessHoursOfBranch.setMorningClose(branchRequest.getMorningClose());
        businessHoursOfBranch.setAfternoonOpen(branchRequest.getAfternoonOpen());
        businessHoursOfBranch.setAfternoonClose(branchRequest.getAfternoonClose());
        businessHoursOfBranch.setEveningOpen(branchRequest.getEveningOpen());
        businessHoursOfBranch.setEveningClose(branchRequest.getEveningClose());
        businessHoursOfBranch.setDays(
                branchRequest.getDays().stream()
                        .map(day -> DayOfWeek.of(day).name())
                        .collect(Collectors.joining(","))
        );

        branchRepository.saveAndFlush(current);
    }

}