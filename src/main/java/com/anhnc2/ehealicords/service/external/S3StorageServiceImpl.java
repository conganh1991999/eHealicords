package com.anhnc2.ehealicords.service.external;

import com.anhnc2.ehealicords.config.S3Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.File;
import java.net.URL;
import java.time.Duration;

@Service
public class S3StorageServiceImpl implements StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3StorageServiceImpl.class);

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final S3Config s3Config;

    public S3StorageServiceImpl(S3Config s3Config) {
        this.s3Config = s3Config;

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(s3Config.getAccessKeyId(), s3Config.getSecretAccessKey());
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        s3Client =
                S3Client.builder()
                        .region(Region.AP_SOUTHEAST_1)
                        .credentialsProvider(credentialsProvider)
                        .build();

        s3Presigner =
                S3Presigner.builder()
                        .region(Region.AP_SOUTHEAST_1)
                        .credentialsProvider(credentialsProvider)
                        .build();

        LOGGER.info("Init S3StorageService successfully");
    }

    @Override
    public void put(String keyName, File fileObject) {
        LOGGER.info("Send put request with key name = {}", keyName);

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(s3Config.getBucketName())
                        .key(keyName)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(fileObject));
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest =
                DeleteObjectRequest.builder().bucket(s3Config.getBucketName()).key(key).build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    @Override
    public String preSign(String keyName) {
        LOGGER.info("Send presign request with keyName={}", keyName);

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(s3Config.getBucketName())
                        .key(keyName)
                        .contentType("octet-stream")
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(30))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        URL url = presignedRequest.url();

        return url.toString();
    }

    @Override
    public String preSignWithType(String keyName, String type) {
        LOGGER.info("Send presign request with keyName: {}, type: {}", keyName, type);

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(s3Config.getBucketName())
                        .key(keyName)
                        .contentType(type)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(30))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        URL url = presignedRequest.url();

        return url.toString();
    }
}
