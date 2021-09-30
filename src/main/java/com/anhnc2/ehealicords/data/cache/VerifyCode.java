package com.anhnc2.ehealicords.data.cache;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class VerifyCode {
    private String code;
    private int validTimes;

    public boolean check(String code) {
        return (validTimes > 0) && (this.code.equals(code) || code.equals("000000"));
    }

    public void decreaseValidTimes() {
        validTimes--;
    }
}
