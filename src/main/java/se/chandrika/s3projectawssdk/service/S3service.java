package se.chandrika.s3projectawssdk.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class S3service {

    private final S3Client s3Client;

    public S3service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> listAllFiles(String bucketName) {
        ListObjectsV2Response listRes = s3Client.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucketName).build()
        );
        return listRes.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    public String uploadFiles(String bucketName, String filePath) {
        String fileName = Paths.get(filePath).getFileName().toString();
        try {
            PutObjectResponse response = s3Client.putObject(
                    PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                    Paths.get(filePath)
            );
            System.out.println("Uploaded: " + fileName + " (ETag: " + response.eTag() + ")");
            return fileName;
        } catch (Exception e) {
            System.out.println("Upload failed: " + e.getMessage());
            return null;
        }
    }

    public boolean downloadFile(String bucketName, String fileName, String downloadPath) {
        try {
            s3Client.getObject(
                    GetObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                    Paths.get(downloadPath)
            );
            return true;
        } catch (Exception e) {
            System.out.println("Download failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFile(String bucketName, String fileName) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName).key(fileName).build());
            return true;
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }

    public List<String> searchFiles(String bucketName, String searchTerm) {
        return listAllFiles(bucketName).stream()
                .filter(name -> name.contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<String> listAllBuckets() {
        return s3Client.listBuckets().buckets().stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }

    public String uploadFolder(String bucketName, String folderPath) {
        try {
            Path sourceDir = Paths.get(folderPath);
            String folderName = sourceDir.getFileName().toString();
            Path zipFile = Paths.get(folderName + ".zip");
            try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipFile))) {
                Files.walk(sourceDir)
                        .filter(path -> !Files.isDirectory(path))
                        .forEach(path -> {
                            ZipEntry entry = new ZipEntry(sourceDir.relativize(path).toString());
                            try {
                                zs.putNextEntry(entry);
                                Files.copy(path, zs);
                                zs.closeEntry();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
            return uploadFiles(bucketName, zipFile.toString());
        } catch (Exception e) {
            System.out.println("Folder upload failed: " + e.getMessage());
            return null;
        }
    }

   }
