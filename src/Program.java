import java.io.File;
import java.io.FileNotFoundException;

class Settings {

    static String productsPath = "data/Products.txt";
    static String customersPath = "data/Customers.txt";
    static int maxElements = 100;
}

public class Program {


    static boolean isFileExists(String path) throws FileNotFoundException {
        return (new File(path)).exists();
    }

    public static void main(String[] args) {

        Store myStore = new Store();

        Manager manager = new Manager("admin", "admin", myStore);
        myStore.addCart();
        myStore.addCart();

        try {
            if (isFileExists(Settings.customersPath)) {
                myStore.loadCustomers();
            } else
                System.out.println("Customers file not found!");
            if (isFileExists(Settings.productsPath)) {
                myStore.loadProducts();
            } else
                System.out.println("Products file not found!");
        } catch (NullPointerException | FileNotFoundException n) {
            System.out.println("Error while importing");
//
////            Manual import products
//            myStore.productsList.add(new Product("Greygoose", 120, 10));
//            myStore.productsList.add(new Product("Jack Daniels", 100, 15));
//            myStore.productsList.add(new Product("Van Goh", 90, 5));
//            myStore.productsList.add(new Product("Roberto Cavelli", 300, 25));
//            myStore.productsList.add(new Product("Red Label", 80, 3));
//            myStore.productsList.add(new Product("Black Label", 100, 12));
//            myStore.productsList.add(new Product("Blue Label", 600, 25));
//            myStore.productsList.add(new Product("Green Fairy", 400, 18));
////            Manual import customers
//            myStore.signUp("user1", "pass", "Street", "0123456789", User.Membership.Bronze);
//            myStore.signUp("user2", "pass", "Street", "0123456789", User.Membership.Gold);
//            myStore.signUp("ari", "pass", "Street", "0123456789", User.Membership.Platinum);
//            myStore.signUp("ari2", "pass", "Street", "0123456789", User.Membership.Gold);
//            myStore.signUp("user3", "pass", "Street", "0123456789", User.Membership.Bronze);
        } finally {
            Menus.init(myStore, manager);

        }

    }
}

