package com.anhnc2.ehealicords.service.external;

import java.util.Map;

public interface MailService {
    void sendEmail(String to, String subject, String template, Map<String, Object> context);
}
