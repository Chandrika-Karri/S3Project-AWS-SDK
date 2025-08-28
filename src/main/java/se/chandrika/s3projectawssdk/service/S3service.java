package se.chandrika.s3projectawssdk.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3service {

    private final S3Client s3Client;

    // S3Client will be auto-injected by Spring
    public S3service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> listAllFiles(String bucketName) {
        ListObjectsV2Request listReq = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listRes = s3Client.listObjectsV2(listReq);

        return listRes.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}
