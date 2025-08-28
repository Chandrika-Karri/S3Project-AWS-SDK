package se.chandrika.s3projectawssdk.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3config {

    @Bean
    public S3Client s3Client() {
        Dotenv dotenv = Dotenv.load(); // loads .env file

        String accessKey = dotenv.get("ACCESS_KEY");
        String secretKey = dotenv.get("SECRET_KEY");

        return S3Client.builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .region(Region.EU_NORTH_1)
                .build();
    }
    @Bean
    public String bucketName() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("BUCKET_NAME");
    }
}
