package com.anhnc2.ehealicords.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceResult {
    long balance;
    long updatedTs;
}
