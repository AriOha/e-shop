import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
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

    void addProductToStore() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        String pName;
        double price;
        int discount;
        do {
            System.out.println("Enter Product name:");
            pName = s.nextLine();
        } while (!Validators.validProductName(pName, managedStore));
        do {
            System.out.println("Enter price:");
            price = s.nextDouble();
        } while (price <= 0);
        do {
            System.out.println("Enter discount(0-50):");
            discount = s.nextInt();
        } while (!Validators.validRange(discount, 0, 50));
        managedStore.addProductToStore(pName, price, discount);
    }

    void changePrice() {
        if (managedStore.displayProducts()) {
            Scanner s = new Scanner(System.in);
            System.out.println("Insert product ID to change it's price");
            Product product = managedStore.searchForProductInStore(s.nextLine());
            if (product != null) {
                if (changePrice(product)) {
                    System.out.println("Price changed successfully");
                } else System.out.println("Price is invalid");
            } else
                System.out.println("Product not found.");
        }
    }

    boolean changePrice(Product p) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert new price");
        return p.changePrice(s.nextDouble());
    }

    void changeDiscount() {
        if (managedStore.displayProducts()) {
            Scanner s = new Scanner(System.in);
            System.out.println("Insert product ID to change it's discount");
            Product product = managedStore.searchForProductInStore(s.nextLine());
            if (product != null) {
                if (changePrice(product)) {
                    System.out.println("Discount changed successfully");
                } else System.out.println("Discount is invalid");
            } else
                System.out.println("Product not found.");
        }
    }

    boolean changeDiscount(Product p) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert new discount");
        return p.changeDiscount(s.nextInt());
    }

    void removeCustomerByName(String givenUsername) {
        try {
            Customer customer = managedStore.searchForCustomer(givenUsername);
            managedStore.deleteCustomer(customer);
        } catch (NullPointerException e) {
            System.out.println("Unable to remove " + givenUsername + ", user not found.");
        }
    }

    void displayCarts() {
        if (managedStore.cartList.size() == 0) {
            System.out.println("No carts in the store.");
        }
        for (Cart cart : managedStore.cartList) {
            System.out.println("Cart no. " + cart.getCartId() + "\t" + "Status: " + (cart.getOwnedByCustomer() != null ? "Belongs to " + cart.ownedByCustomer.getUserName() + "\t" : "Available"));
        }
    }

    void addCartToStore(int amount) {

        if (amount < 0) {
            System.out.println("Amount is incorrect [" + amount + "]");
            return;
        }
        if (amount == 0) {
            System.out.println("No carts added.");
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

    void removeProductFromStore() {
        if (managedStore.displayProducts()) {
            System.out.println("Select product to delete by ID:");
            Scanner s = new Scanner(System.in);
            try {
                removeProductFromStore(s.nextLine());
            } catch (NumberFormatException n) {
                System.out.println("ID format not valid");
            } catch (NullPointerException nu) {
                System.out.println("Product not found by provided ID");
            }
        }
    }

    void removeProductFromStore(String catalogNumber) {
        Product productToRemove = managedStore.searchForProductInStore(catalogNumber);
        if (productToRemove != null) {
            if (managedStore.removeItem(productToRemove))
                System.out.println(productToRemove.getProductName() + " removed from the store");
        } else System.out.println("Product not found in the store.");
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
        String selection = "";
        System.out.println("Select one of the options above:");
        while (!selection.equals("exit")) {
            System.out.println(
                    "=======\n1) Store Management.\n" +
                            "2) Customers Management.\n" +
                            "3) Products Management.\n" +
                            "4) Exit.\n" + "=======");
            selection = s.nextLine();
            try {
                switch (selection) {
                    case "1" -> storeMenu();
                    case "2" -> customersMenu();
                    case "3" -> productsMenu();
                    case "4" -> {
                        System.out.println("Save to files before exit? y/n");
                        if (s.nextLine().startsWith("y")) {
                            managedStore.saveCustomers();
                            managedStore.saveProducts();
                        }
                        logout();
                        selection = "exit";
                    }
                    default -> System.out.println("Selection not recognized, try again.");
                }
            } catch (NumberFormatException numFor) {
                System.out.println("Invalid value of number");
            } catch (IOException f) {
                System.out.println("File not found.");
            }
        }
    }

    void storeMenu() {
        Scanner s = new Scanner(System.in);
        String selection = "";
        while (!selection.equals("exit")) {
            System.out.println(
                    "=======\n" +
                            "Store management:\n" +
                            "1) Display carts status.\n" +
                            "2) Add carts to the shop.\n" +
                            "3) Delete cart from the shop.\n" +
                            "4) Amount of carts in use.\n" +
                            "5) Display sales reports.\n" +
                            "6) Back.\n" + "=======");
            selection = s.nextLine();
            try {
                switch (selection) {
                    case "1" -> displayCarts();
                    case "2" -> {
                        System.out.println("How much carts to add? (current " + managedStore.getCartsInUse() + "/" + managedStore.getMaxCarts() + "):");
                        addCartToStore(Integer.parseInt(s.nextLine()));
                    }
                    case "3" -> removeCartFromStore();
                    case "4" -> displayTakenCarts();
                    case "5" -> printTotalSales();
                    case "6" -> selection = "exit";
                    default -> System.out.println("Selection not recognized, try again.");
                }
            } catch (NumberFormatException numFor) {
                System.out.println("Invalid value of number");
            }
        }
    }

    void customersMenu() {
        Scanner s = new Scanner(System.in);
        String selection = "";
        while (!selection.equals("exit")) {
            System.out.println(
                    "=======\n" +
                            "Customers management:\n" +
                            "1) Show registered customers.\n" +
                            "2) Add new customer.\n" +
                            "3) Remove customer.\n" +
                            "4) Show online customers.\n" +
                            "5) Save customers to file.\n" +
                            "6) Back.\n=======");
            selection = s.nextLine();
            try {
                switch (selection) {
                    case "1":
                        managedStore.displayCustomers();
                        break;
                    case "2":
                        if (managedStore.registerCustomer(membership))
                            System.out.println("User added successfully");
                        break;
                    case "3":
                        if (managedStore.displayCustomers()) {
                            System.out.println("Insert Username:");
                            removeCustomerByName(s.nextLine());
                        }
                        break;
                    case "4":
                        displayOnlineCustomers();
                        break;
                    case "5":
                        managedStore.saveCustomers();
                        break;
                    case "6":
                        selection = "exit";
                        break;
                    default:
                        System.out.println("Selection not recognized, try again.");
                }
            } catch (NumberFormatException numFor) {
                System.out.println("Invalid value of number");
            } catch (FileNotFoundException f) {
                System.out.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void productsMenu() {
        Scanner s = new Scanner(System.in);
        String selection = "";
        System.out.println("");
        while (!selection.equals("exit")) {
            System.out.println(
                    "=======\n" +
                            "Products management:\n" +
                            "1) Display products list.\n" +
                            "2) Add new product\n" +
                            "3) Change price\n" +
                            "4) Chane discount for product\n" +
                            "5) Remove product from the store\n" +
                            "6) Save to a file\n" +
                            "7) Back.\n" + "=======");
            selection = s.nextLine();
            try {
                switch (selection) {
                    case "1" -> {
                        managedStore.displayProducts();
                    }
                    case "2" -> addProductToStore();
                    case "3" -> changePrice();
                    case "4" -> changeDiscount();
                    case "5" -> removeProductFromStore();
                    case "6" -> managedStore.saveProducts();
                    case "7" -> selection = "exit";
                    default -> System.out.println("Selection not recognized, try again.");
                }
            } catch (NumberFormatException | InputMismatchException numFor) {
                System.out.println("Invalid value of number");
            } catch (FileNotFoundException f) {
                System.out.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

