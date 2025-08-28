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
                    System.out.println("Nu listas alla filer");
                    List<String> files = s3service.listAllFiles(bucketName);
                    files.forEach(System.out::println);

                    // lists alla filer  som heter "chandrikakarri-s3demoproject"
                    break;
                case 2:
                    System.out.println("Vilken fil vill du ladda upp");
                    break;
                case 3:
                    System.out.println("Vilken fil vill du ladda ner fil");
                    break;
                case 4:
                    return;
            }
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(S3ProjectAwsSdkApplication.class, args);
    }


}

