package com.anhnc2.ehealicords.service.external;

import com.anhnc2.ehealicords.config.SmsConfig;
import com.anhnc2.ehealicords.util.NumberUtil;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsServiceImpl implements SmsService {
    public static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsServiceImpl.class);

    private final SmsConfig smsConfig;
    private final TwilioRestClient twilioRestClient;

    public TwilioSmsServiceImpl(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
        twilioRestClient =
                new TwilioRestClient.Builder(smsConfig.getSid(), smsConfig.getToken()).build();
        LOGGER.info("Init twilio service successfully");
    }

    @Override
    public void send(String phoneNumber, String content) {
        LOGGER.info("Send sms message to phoneNumber={}, content={}", phoneNumber, content);

        if (smsConfig.isEnable()) {
            Message.creator(
                    new PhoneNumber(NumberUtil.formatPhoneNumber(phoneNumber)),
                    new PhoneNumber(smsConfig.getSourceNumber()),
                    content)
                    .create(twilioRestClient);
            LOGGER.info("Send message successfully: message={}", content);
        } else {
            LOGGER.info("Twilio is disabled to save money: message={}", content);
        }
    }
}
