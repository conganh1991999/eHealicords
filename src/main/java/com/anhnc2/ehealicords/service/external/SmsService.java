package com.anhnc2.ehealicords.service.external;

public interface SmsService {
    void send(String phoneNumber, String content);
}
