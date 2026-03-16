package view;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);

    public Map.Entry<String, String> displayLoginView() {
        System.out.print("[SYSTEM] Enter username: ");
        String username = scanner.nextLine();
        System.out.print("[SYSTEM] Enter password: ");
        String password = scanner.nextLine();

        return new AbstractMap.SimpleEntry<>(username, password);
    }
    
}
