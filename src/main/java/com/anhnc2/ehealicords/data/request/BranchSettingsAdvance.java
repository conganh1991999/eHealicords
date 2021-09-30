package com.anhnc2.ehealicords.data.request;

import lombok.Data;

@Data
public class BranchSettingsAdvance {
    Integer minutePerShift;
    Integer minuteDeposit;
    Long feeAppointment;
    Long feeConsulting;
}
