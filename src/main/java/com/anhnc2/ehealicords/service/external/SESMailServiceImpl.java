package com.anhnc2.ehealicords.service.external;

import com.anhnc2.ehealicords.config.SESConfig;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Service
public class SESMailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SESMailServiceImpl.class);

    private final SesAsyncClient sesClient;
    private final SESConfig sesConfig;
    private final SpringTemplateEngine templateEngine;

    public SESMailServiceImpl(SESConfig sesConfig, SpringTemplateEngine templateEngine) {
        this.sesConfig = sesConfig;
        this.templateEngine = templateEngine;

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(sesConfig.getAccessKeyId(), sesConfig.getSecretAccessKey());

        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        sesClient =
                SesAsyncClient.builder()
                        .region(Region.AP_SOUTHEAST_1)
                        .credentialsProvider(credentialsProvider)
                        .build();
    }

    @Override
    public void sendEmail(String to, String subject, String template, Map<String, Object> params) {
        Context context = new Context();
        context.setVariables(params);

        // The HTML body for the email.
        String mailContent = templateEngine.process(template, context);

        Destination destination = Destination.builder().toAddresses(to).build();

        Content mailSubject = Content.builder().charset("UTF-8").data(subject).build();

        Body body =
                Body.builder().html(Content.builder().charset("UTF-8").data(mailContent).build()).build();

        Message message = Message.builder().body(body).subject(mailSubject).build();

        SendEmailRequest request =
                SendEmailRequest.builder()
                        .destination(destination)
                        .message(message)
                        .source(sesConfig.getSourceEmail())
                        .build();

        sesClient.sendEmail(request);
        LOGGER.debug("Sent email to {}", to);
    }
}
