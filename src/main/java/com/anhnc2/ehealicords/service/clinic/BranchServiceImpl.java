package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.constant.BranchStatus;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.BusinessHourEntity;
import com.anhnc2.ehealicords.data.request.BranchRequest;
import com.anhnc2.ehealicords.data.request.BranchSettingsAdvance;
import com.anhnc2.ehealicords.exception.BranchException;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.BusinessHourRepository;
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
    private final BusinessHourRepository businessHourRepository;

    @Override
    public List<BranchEntity> getAllBranch() {
        return branchRepository.findAll();
    }

    @Override
    public BranchEntity getBranchById(int id) {
        return branchRepository
                .findById(id)
                .orElseThrow(() -> new BranchException(StatusCode.BRANCH_NOT_EXISTED));
    }

    @Override
    @Transactional
    public void createBranch(BranchRequest branch) {
        BusinessHourEntity businessHour = BusinessHourEntity.builder()
                .morningOpen(branch.getMorningOpen())
                .morningClose(branch.getMorningClose())
                .afternoonOpen(branch.getAfternoonOpen())
                .afternoonClose(branch.getAfternoonClose())
                .eveningOpen(branch.getEveningOpen())
                .eveningClose(branch.getEveningClose())
                .days(branch.getDays().stream().map(day -> DayOfWeek.of(day).name())
                        .collect(Collectors.joining(",")))
                .build();

        businessHourRepository.saveAndFlush(businessHour);

        BranchEntity branchDAO = BranchEntity.builder()
                .name(branch.getName())
                .provinceId(branch.getProvinceId())
                .districtId(branch.getDistrictId())
                .wardId(branch.getWardId())
                .address(branch.getAddress())
                .fullAddress(branch.getFullAddress())
                .status(BranchStatus.ACTIVE)
                .businessHourId(businessHour.getId())
                .minutePerShift(60)
                .minuteDeposit(5)
                .feeAppointment(100000L)
                .feeConsulting(5000L)
                .build();

        LOGGER.debug("Create BranchEntity: {}", branchDAO);

        branchRepository.saveAndFlush(branchDAO);
    }

    @Override
    @Transactional
    public void updateBranch(BranchRequest branch) {
        LOGGER.debug("Update BranchEntity: {}", branch);
        BranchEntity branchDAOCurrent =
                branchRepository
                        .findById(branch.getId())
                        .orElseThrow(() -> new BranchException(StatusCode.BRANCH_NOT_EXISTED));

        BusinessHourEntity businessHourOfBranch = branchDAOCurrent.getBusinessHourEntity();

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

    @Override
    public int getMinutePerShiftByBranchId(int branchId) {
        return branchRepository.findById(branchId).get().getMinutePerShift();
    }

    @Override
    public int getMinuteDepositByBranchId(int branchId) {
        return branchRepository.findById(branchId).get().getMinuteDeposit();
    }

    @Override
    public void updateAdvanceSettings(int branchId, BranchSettingsAdvance settings) {
        BranchEntity branchDAO = branchRepository.findById(branchId).get();
        branchDAO.setMinuteDeposit(settings.getMinuteDeposit());
        branchDAO.setMinutePerShift(settings.getMinutePerShift());
        branchDAO.setFeeAppointment(settings.getFeeAppointment());
        branchDAO.setFeeConsulting(settings.getFeeConsulting());

        branchRepository.saveAndFlush(branchDAO);
    }
}