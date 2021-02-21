import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menus {

    static void init(Store myStore, Manager manager){
        Scanner s = new Scanner(System.in);
        String selection = "";
        while (!selection.equals("4")) {
            System.out.println("Select an option:\n" +
                    "1) login\n" +
                    "2) Register\n" +
                    "3) Reset login status\n" +
                    "4) Exit");
            selection = s.nextLine();
            switch (selection) {
                case "1" -> {
                    if(!login(myStore, manager))
                        System.out.println("Failed to login.");
                }
                case "2" -> registerCustomer(myStore);
                case "3" -> {
                    System.out.println("Enter your username:");
                    Customer user = myStore.searchForCustomer(s.nextLine());
                    System.out.println("Enter your password:");
                    user.remoteLogout(s.nextLine());
                }
                default -> System.out.println("Invalid selection, try again.");
            }
        }
    }

    static boolean login(Store myStore, Manager manager) {
        Customer currentUser;
        boolean success = false;
        Scanner s = new Scanner(System.in);
        String username, password;
        System.out.println("Enter Username:");
        username = s.nextLine();
        System.out.println("Enter Password:");
        password = s.nextLine();
        if (manager.login(username, password)) {
            manager.menu();
            success = true;
        } else
            for (Customer customer : myStore.customersList)
                if (customer.getUserName().equals(username)) {
                    if (customer.login(password)) {
                        success = true;
                        currentUser = customer;
                        currentUser.menu(myStore);
                        break;
                    }
                }
        return (success);
    }

    static boolean registerCustomer(Store myStore) {
        return myStore.registerCustomer();
    }

}
