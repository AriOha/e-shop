import java.io.File;
import java.io.FileNotFoundException;

public class Program {

    static boolean isFileExists(String path) throws FileNotFoundException {
        return (new File(path)).exists();

    }

    public static void main(String[] args) throws FileNotFoundException {

        String productsPath = "data/Products.txt";
        String customersPath = "data/Customers.txt";

        Store myStore = new Store();
        Manager manager = new Manager("ari", "pass", myStore);

//        myStore.signUp("user1", "pass", "Street", "0123456789", regular);
//        myStore.signUp("user2", "pass", "Street", "0123456789", regular);
//        myStore.signUp("ari", "pass", "Street", "0123456789", User.Membership.Platinum);
//        myStore.signUp("ari2", "pass", "Street", "0123456789", regular);
//        myStore.signUp("user3", "pass", "Street", "0123456789", bronze);
//        myCustomer = myStore.customersList.get(0);
//        myCustomer2 = myStore.customersList.get(1);
//        myVIPCustomer = (VIPCustomer) myStore.customersList.get(2);
//        myCustomer.login("pass");

//        myStore.productsList.add(new Product("Greygoose", 120, 10));
//        myStore.productsList.add(new Product("Jack Daniels", 100, 15));
//        myStore.productsList.add(new Product("Van Goh", 90, 5));
//        myStore.productsList.add(new Product("Roberto Cavelli", 300, 25));
//        myStore.productsList.add(new Product("Red Label", 80, 3));
//        myStore.productsList.add(new Product("Black Label", 100, 12));
//        myStore.productsList.add(new Product("Blue Label", 600, 25));
//        myStore.productsList.add(new Product("Green Fairy", 400, 18));

//        myStore.addCart();
        if (isFileExists(customersPath)) {
            myStore.loadCustomers(customersPath);
        }
        if (isFileExists(productsPath)) {
            myStore.loadProducts(productsPath);
        }
        Menus.init(myStore, manager);

    }
}

