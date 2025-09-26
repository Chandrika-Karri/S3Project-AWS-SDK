package se.chandrika.s3projectawssdk.service;

import org.springframework.stereotype.Service;
import se.chandrika.s3projectawssdk.component.ConsoleInput;

import java.util.List;

@Service
public class ServiceList {

    private final S3service s3service;
    private final String bucketName;
    private final ConsoleInput console;
    private String currentBucket;

    public ServiceList(S3service s3service, String bucketName, ConsoleInput console) {
        this.s3service = s3service;
        this.bucketName = bucketName;
        this.console = console;
    }

    public void start() {
        currentBucket = bucketName;

        while (true) {
            printMenu();
            int choice = console.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> listFiles();
                case 2 -> uploadFile();
                case 3 -> downloadFile();
                case 4 -> deleteFile();
                case 5 -> searchFiles();
                case 6 -> chooseBucket();
                case 7 -> uploadFolder();
                case 8 -> { console.println("Exiting..."); return; }
                default -> console.println("Invalid choice. Try again.");
            }
        }
    }

    private void printMenu() {
        console.println("\n--- AWS S3 Menu ---");
        console.println("1. List all files in the bucket");
        console.println("2. Upload file to the bucket");
        console.println("3. Download file from the bucket");
        console.println("4. Delete file from the bucket");
        console.println("5. Search required files from the bucket");
        console.println("6. Choose bucket");
        console.println("7. Upload zipped folder to the bucket");
        console.println("8. Exit");
    }

    private void listFiles() {
        console.println("Files in bucket: " + currentBucket);
        s3service.listAllFiles(currentBucket).forEach(console::println);
    }

    private void uploadFile() {
        String path = console.readLine("Enter file path to upload: ");
        String uploaded = s3service.uploadFiles(currentBucket, path);
        console.println(uploaded != null ? "Uploaded: " + uploaded : "Upload failed.");
    }

    private void downloadFile() {
        String name = console.readLine("Enter file name to download: ");
        String dest = console.readLine("Enter local download path: ");
        boolean ok = s3service.downloadFile(currentBucket, name, dest);
        console.println(ok ? "Downloaded successfully." : "Download failed.");
    }

    private void deleteFile() {
        String name = console.readLine("Enter file name to delete: ");
        boolean ok = s3service.deleteFile(currentBucket, name);
        console.println(ok ? "Deleted successfully." : "Delete failed.");
    }

    private void searchFiles() {
        String term = console.readLine("Enter search term: ");
        s3service.searchFiles(currentBucket, term).forEach(console::println);
    }

    private void chooseBucket() {
        List<String> buckets = s3service.listAllBuckets();
        if (buckets.isEmpty()) {
            console.println("No buckets found.");
            return;
        }
        for (int i = 0; i < buckets.size(); i++) {
            console.println((i + 1) + ". " + buckets.get(i));
        }
        int choice = console.readInt("Select a bucket: ");
        if (choice >= 1 && choice <= buckets.size()) {
            currentBucket = buckets.get(choice - 1);
            console.println("Current bucket set to: " + currentBucket);
        } else {
            console.println("Invalid choice.");
        }
    }

    private void uploadFolder() {
        String folder = console.readLine("Enter folder path to upload: ");
        String uploaded = s3service.uploadFolder(currentBucket, folder);
        console.println(uploaded != null ? "Folder uploaded as: " + uploaded : "Folder upload failed.");
    }

}
