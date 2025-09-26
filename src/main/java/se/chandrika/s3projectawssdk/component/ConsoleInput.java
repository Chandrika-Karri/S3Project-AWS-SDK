package se.chandrika.s3projectawssdk.component;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public void println(String message) {
        System.out.println(message);
    }

}
