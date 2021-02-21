import java.io.FileNotFoundException;
import java.util.Scanner;

public class Manager extends User {

    Store managedStore;

    Manager(String userName, String password) {
        this(userName, password, null);
    }

    Manager(String userName, String password, Store managedStore) {
        super(userName, password, Membership.Manager);
        this.managedStore = managedStore;
    }

//    boolean registerCustomer() {
//        Scanner s = new Scanner(System.in);
//        String username = "";
//        String pass = "";
//        String address = "";
//        String phone = "";
//        boolean validUsername, validPassword, validAddr, validPhone;
//        validUsername = validPassword = validAddr = validPhone = false;
//
//        do {
//            if (!validUsername) {
//                System.out.println("Enter username: ");
//                username = s.nextLine();
//            }
//            if (!validPassword) {
//                System.out.println("Enter password: ");
//                pass = s.nextLine();
//            }
//            if (!validAddr) {
//                System.out.println("Enter address: ");
//                address = s.nextLine();
//            }
//            if (!validPhone) {
//                System.out.println("Enter phone number: ");
//                phone = s.nextLine();
//            }
//
//            validUsername = Validators.validUserName(username, managedStore);
//            validPassword = Validators.validPassword(pass);
//            validAddr = Validators.validAddress(address);
//            validPhone = Validators.phoneValidator(phone);
//        } while (!(validUsername && validPassword && validAddr && validPhone));
//
//
//        System.out.println("Details are valid, define as VIP? y/n");
//        if (s.nextLine().startsWith("y")) {
//            System.out.println("Creating VIP customer.");
//            return managedStore.signUp(username, pass, address, phone, Membership.Bronze);
//        } else {
//            System.out.println("Creating regular customer.");
//            return managedStore.signUp(username, pass, address, phone, Membership.Basic);
//        }
//    }

    void removeCustomerByName(String givenUsername) {
        try {
            Customer customer = managedStore.searchForCustomer(givenUsername);
            managedStore.deleteCustomer(customer);
        } catch (NullPointerException e) {
            System.out.println("Unable to remove " + givenUsername + ", user not found.");
        }
    }

    void displayCarts() {
        for (Cart cart : managedStore.cartList) {
            System.out.println("Cart no. " + cart.getCartId() + "\t" + "Status: " + (cart.getOwnedByCustomer() != null ? "Belongs to " + cart.ownedByCustomer.getUserName() + "\t" : "Available"));
        }
    }

    void addCartToStore() {
        addCartToStore(1);
    }

    void addCartToStore(int amount) {

        if (amount <= 0) {
            System.out.println("amount is incorrect [" + amount + "]");
            return;
        }
        int added = 0;
        for (int i = 0; i < amount && managedStore.addCart(); i++)
            added++;
        if (added == 0)
            System.out.println("Unable to add more carts to the store, " + "(" + managedStore.cartList.size() + " total)");
        else
            System.out.println("added " + added + " cart/s to the store" + "(" + managedStore.cartList.size() + " total)");
    }

    void removeCartFromStore() {
        displayCarts();
        System.out.println("Select cart to delete by ID:");
        Scanner s = new Scanner(System.in);
        try {
            managedStore.removeCart(s.nextLine());
        } catch (NumberFormatException n) {
            System.out.println("ID format not valid");
        } catch (NullPointerException nu) {
            System.out.println("Cart not found by provided ID");
        }
    }

    void displayOnlineCustomers() {
        managedStore.displayOnlineCustomers();
    }

    void displayTakenCarts() {
        managedStore.takenCarts();
    }

    void printTotalSales() {
        managedStore.earningsReport();
    }

    void menu() {
        Scanner s = new Scanner(System.in);
        int amount;
        String selection = "";
        System.out.println("Welcome " + userName);
        System.out.println("Select one of the options above:");

        while (!selection.equals("exit")) {
            System.out.println(
                    "=======\n1) Register new customer.\t" +
                            "5) Remove customer.\n" +
                            "2) Add carts to the shop.\t" +
                            "6) Delete cart from the shop.\n" +
                            "3) Show online customers.\t" +
                            "7) Amount of carts in use.\n" +
                            "4) Display sales reports.\t" +
                            "8) Show registered customers.\n" +
                            "9) Exit.\n" + "=======");
            selection = s.nextLine();
            try {

                switch (selection) {
                    case "1":
                        if (managedStore.registerCustomer(membership))
                            System.out.println("User added successfully");
                        break;
                    case "2":
                        System.out.println("How much carts to add? (current " + managedStore.getCartsInUse() + "/" + managedStore.getMaxCarts() + "):");
                        addCartToStore(Integer.parseInt(s.nextLine()));
                        break;
                    case "3":
                        displayOnlineCustomers();
                        break;
                    case "4":
                        printTotalSales();
                        break;
                    case "5":
                        managedStore.displayCustomers();
                        System.out.println("Insert Username:");
                        removeCustomerByName(s.nextLine());
                        break;
                    case "6":
                        removeCartFromStore();
                        break;
                    case "7":
                        displayTakenCarts();
                        break;
                    case "8":
                        managedStore.displayCustomers();
                        break;
                    case "9":
                        logout();
                        selection = "exit";
                        break;
                    case "10":
                        System.out.println("Saving data..");
                        managedStore.saveCustomers();
                        break;
                    case "11":
                        System.out.println("Loading customers..");
                        managedStore.loadCustomers();
                        break;
                    default:
                        System.out.println("Selection not recognized, try again.");
                }
            } catch (NumberFormatException numFor) {
                System.out.println("Invalid value of number");
            } catch (FileNotFoundException fn) {
                System.out.println("File not found");

            }
        }

    }

}

