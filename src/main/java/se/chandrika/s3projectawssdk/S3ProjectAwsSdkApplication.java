package se.chandrika.s3projectawssdk;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.chandrika.s3projectawssdk.service.S3service;
import se.chandrika.s3projectawssdk.service.ServiceList;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class S3ProjectAwsSdkApplication implements CommandLineRunner {

    private final ServiceList serviceList;

    public S3ProjectAwsSdkApplication(ServiceList serviceList) {
        this.serviceList = serviceList;
    }

    public static void main(String[] args) {
        SpringApplication.run(S3ProjectAwsSdkApplication.class, args);
    }

    @Override
    public void run(String... args) {
        serviceList.start();
    }

}

