package se.chandrika.s3projectawssdk;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.chandrika.s3projectawssdk.service.S3service;
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

    @Autowired
    private S3service s3service;

    @Autowired
    private String bucketName;


    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Lista alla filer");
            System.out.println("2. Ladda upp fil");
            System.out.println("3. Ladda ner");
            System.out.println("4. Avsluta");
            System.out.print("Choose an option:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("List all files in the bucket:");
                    List<String> files = s3service.listAllFiles(bucketName);
                    files.forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("Enter the file path to upload:");
                    String filePath = scanner.nextLine();
                    String uploadedFileName = s3service.uploadFiles(bucketName, filePath);
                    if (uploadedFileName != null) {
                        System.out.println("File uploaded successfully: " + uploadedFileName);
                    } else {
                        System.out.println("File upload failed.");
                    }
                    break;
                case 3:
                    System.out.println("Which file do you want to download?");
                    String fileName = scanner.nextLine();
                    System.out.println("Downloading filepath: ");
                    String downloadPath = scanner.nextLine();
                    boolean downloadSuccess = s3service.downloadFile(bucketName, fileName, downloadPath);
                    if (downloadSuccess) {
                        System.out.println("File downloaded successfully to: " + downloadPath);
                    } else {
                        System.out.println("File download failed.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(S3ProjectAwsSdkApplication.class, args);
    }


}

