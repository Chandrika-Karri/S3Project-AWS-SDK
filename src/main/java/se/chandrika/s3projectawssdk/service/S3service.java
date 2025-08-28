package se.chandrika.s3projectawssdk.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Paths;
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

    public String uploadFiles(String bucketName, String filePath) {
        // Implementation for uploading a file to S3
        String fileName = extractFileName(filePath);

        try {
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putReq, Paths.get(filePath));

            System.out.println("Uploaded file: " + fileName + " with ETag: " + response.eTag());
            return fileName;

        } catch (Exception e) {
            System.out.println("Failed to upload file: " + e.getMessage());
            return null;
        }
    }

    public boolean downloadFile(String bucketName, String fileName, String downloadPath) {

        try {
            GetObjectRequest getReq = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.getObject(getReq, Paths.get(downloadPath));
            System.out.println("Downloaded file: " + fileName + " to " + downloadPath);
            return true;

        } catch (Exception e) {
            System.out.println("Failed to download file: " + e.getMessage());
            return false;
        }
    }
    private String extractFileName(String filePath) {
        return Paths.get(filePath).getFileName().toString();
    }
}
