import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Customer extends User {

    protected String address, phoneNumber;
    protected double totalPayed;
    protected int totalItems;
    Cart cart;

    Customer(String UserName, String password, String address, String phoneNumber) {
        this(UserName, password, address, phoneNumber, 0, 0);
    }

    Customer(String UserName, String password, String address, String phoneNumber, double totalPayed, int totalItems) {
        super(UserName, password);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        this.totalPayed = totalPayed;
        this.totalItems = totalItems;
    }

    Customer(Scanner s) throws FileNotFoundException {
        this(s.next(), s.next(), s.next(), s.next(), s.nextDouble(), s.nextInt());
    }


    public Cart getCart() {
        return cart;
    }


    public String getAddress() {
        return address;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String getUserName() {
        return super.getUserName();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10)
            if (Validators.phoneValidator(phoneNumber))
                this.phoneNumber = phoneNumber;
    }


    void changeAddress(String newAddress) {
        if (Validators.validAddress(newAddress))
            if (!this.address.equals(newAddress)) {
                setAddress(newAddress);
            }
    }


    void changePhone(String newPhoneNumber) {
        if (!this.phoneNumber.equals(newPhoneNumber)) {
            setPhoneNumber(newPhoneNumber);
        }
    }


    boolean takeCart(Cart cart) {
        if (cart.getOwnedByCustomer() != null) {
            System.out.println("Cart " + cart.getCartId() + " already taken");
        } else if (this.cart == null) {
            this.cart = cart;
            this.cart.setCustomer(this);
            return true;
        } else System.out.println("User already has a cart!");
        return false;
    }

    boolean findAvailableCart(Store store) {
        for (Cart cart : store.cartList)
            if (cart.getOwnedByCustomer() == null) {
                return takeCart(cart);
            }
        System.out.println("No carts available.");
        return false;
    }


    boolean releaseCart() {
        if (this.cart != null) {
            this.cart.emptyCart();
            this.cart.releaseCustomerFromCart();
            this.cart = null;
            return true;
        }
        return false;
    }

    void emptyCart() {
        if (this.cart != null)
            this.cart.emptyCart();
    }

    void addProductToCart(Store storeToSearch, String productId) {
        Product product = storeToSearch.searchForProductInStore(productId);
        addProductToCart(product);
    }

    void addProductToCart(Product item) throws NullPointerException {
        this.cart.addItem(item);
        this.cart.calculateTotal();
    }


    private void displayProductsInCart() {
        if (cart != null) {
            System.out.println("Your cart(id:" + cart.getCartId() + "):");
            for (Product product : cart.productsList) {
                System.out.println(product);
            }
        } else
            throw new NullPointerException();
    }

    boolean removeProductFromCart() {

        if (this.cart != null) {
            displayProductsInCart();
            System.out.println("Select product to delete by ID:");
            Scanner s = new Scanner(System.in);
            try {
                removeProductFromCart(s.nextLine());
            } catch (NumberFormatException n) {
                System.out.println("ID format not valid");
            } catch (NullPointerException nu) {
                System.out.println("Product not founded not found by provided ID");
            }
            return true;
        }
        return false;
    }

    void removeProductFromCart(String catalogNumber) {
        Product productToRemove = cart.searchForProduct(catalogNumber);
        if (productToRemove != null) {
            if (cart.removeItem(productToRemove))
                System.out.println(productToRemove.getProductName() + " removed from cart");
        } else System.out.println("Product not found in cart.");
        cart.calculateTotal();
    }


    boolean displayBill() {
        if (this.cart != null) {
            double totalPrice = cart.calculateTotal();
            String checkout = cart.getBillList() +
                    "========================\n" +
                    "Total before discounts:\t" + totalPrice +
                    "\nSaved money:\t\t\t" + cart.calculateDiscounts() +
                    "\nAfter discount:\t\t\t" + (cart.totalPrice - cart.calculateDiscounts());
            if (totalPrice != 0) {
                System.out.println(checkout);
                return true;
            } else System.out.println("No items in Cart.");
        } else System.out.println("No cart assigned to you.");
        return false;
    }


    boolean pay() {
        if (cart != null) {
            totalPayed += cart.totalPrice - cart.calculateDiscounts();
            totalItems += cart.productsList.size();
            cart.emptyCart();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(userName).append('\t');
        sb.append("address: ").append(address).append('\t');
        sb.append("number: ").append(phoneNumber).append('\t');
        sb.append("Total payed: ").append(totalPayed).append('\t');
        sb.append("Total bought: ").append(totalItems).append('\t');
        sb.append("|").append(isLoggedIn).append("|\t");
        sb.append("|").append(membership).append("|");
        return sb.toString();
    }

    public String shortInfo() {
        final StringBuilder sb = new StringBuilder();
        sb.append("|").append(membership).append("|:|");
        sb.append(userName).append("| ~ ");
        sb.append("|phone: ").append(phoneNumber).append("| ~ |");
        sb.append("Total payed: ").append(totalPayed).append(", ");
        sb.append("Total bought: ").append(totalItems).append("|  ");
        sb.append(isLoggedIn ? "|Online|" : "|Offline|");
        return sb.toString();
    }

    boolean changeAddress() {
        Scanner sd = new Scanner(System.in);
        String address;
        System.out.println("Your current address is: " + getAddress());
        System.out.println("Set new address:");
        address = sd.nextLine();
        if (Validators.validAddress(address)) {
            setAddress(address);
            return true;
        }
        return false;
    }

    boolean changePhoneNumber() {
        Scanner sp = new Scanner(System.in);
        String phone;
        System.out.println("Your current phone number is: " + getPhoneNumber());
        System.out.println("Set new phone number:");
        phone = sp.nextLine();
        if (Validators.phoneValidator(phone)) {
            setPhoneNumber(phone);
            return true;
        }
        return false;
    }

    boolean changePassword() {
        Scanner sp = new Scanner(System.in);
        String oldPassword, newPassword, newPassRepeat;
        System.out.println("Enter your old password:");
        oldPassword = sp.nextLine();
        newPassRepeat = "";
        if (passwordAuthentication(oldPassword)) {
            System.out.println("Set new password:");
            newPassword = sp.nextLine();
            do {
                if (newPassRepeat.equals("CANCEL"))
                    return false;
                System.out.println("Repeat new password:");
                newPassRepeat = sp.nextLine();
                if (!newPassword.equals(newPassRepeat)) {
                    System.out.println("New passwords are not equals(to exit type CANCEL).");
                }
            } while (!newPassword.equals(newPassRepeat));
            if (Validators.validPassword(newPassword)) {
                setPassword(newPassword);
                return true;
            }
        }
        return false;
    }

    public void save(PrintWriter pw) {
        super.save(pw);
        pw.println(address);
        pw.println(phoneNumber);
        pw.println(totalPayed);
        pw.println(totalItems);
    }

//    void menu(Store store) {
//        Scanner s = new Scanner(System.in);
//        String selection = "";
//        System.out.println("Welcome " + userName);
//        System.out.println("Select one of the options above:");
//
//        while (!selection.equals("exit")) {
//            System.out.println(
//                    "=======\n1) Take Cart. \t\t\t\t\t" +
//                            "6) Change address. \n" +
//                            "2) Release Cart. \t\t\t\t" +
//                            "7) Change phone number. \n" +
//                            "3) Add product to cart. \t\t" +
//                            "8) Checkout. \n" +
//                            "4) Remove product from cart. \t" +
//                            "9) logout.\n" +
//                            "5) Change password.\t\t\t\t" +
//                            "10) exit(w/o logout).\n" + "=======");
//            selection = s.nextLine();
//            try {
//                switch (selection) {
//                    case "1":
//                        if (findAvailableCart(store))
//                            System.out.println("Cart added successfully");
//                        break;
//                    case "2":
//                        if (releaseCart())
//                            System.out.println("Cart released.");
//                        else
//                            System.out.println("You dont have a cart.");
//                        break;
//                    case "3":
//                        if (store.displayProducts()) {
//                            System.out.println("Insert product ID to add the item to the cart");
//                            addProductToCart(store, s.nextLine());
//                        }
//                        break;
//                    case "4":
//                        if (!removeProductFromCart())
//                            System.out.println("You dont have products in cart.");
//                        break;
//                    case "5":
//                        System.out.println("Password " + (changePassword() ? "changed successfully." : "was not changed."));
//                        break;
//                    case "6":
//                        System.out.println("Address " + (changeAddress() ? "changed successfully." : "was not changed."));
//                        break;
//                    case "7":
//                        System.out.println("Phone " + (changePhoneNumber() ? "changed successfully to - " + getPhoneNumber() : "was not changed."));
//                        break;
//                    case "8":
//                        System.out.println("Your bill:");
//                        if (displayBill()) {
//                            System.out.println("Do you want to pay? y/n");
//                            if (s.nextLine().startsWith("y")) {
//                                if (pay())
//                                    System.out.println("Payment successful.");
//                            }
//                        }
//                        break;
//                    case "9":
//                        logout();
//                    case "10":
//                        selection = "exit";
//                        break;
//                    default:
//                        System.out.println("Selection not recognized, try again.");
//                }
//            } catch (NumberFormatException numFor) {
//                System.out.println("Invalid value of number");
//            } catch (NullPointerException nu) {
//                System.out.println("You dont own a cart.");
//
//            }
//        }
//
//    }

    void menu(Store store) {
        Scanner s = new Scanner(System.in);
        String selection = "";
        System.out.println("Welcome " + userName);
        System.out.println("Select one of the options above:");

        while (!selection.equals("exit")) {
            System.out.println(
                    "=======\n1) Take Cart. \t\t\t\t\t" +
                            "6) Change address. \n" +
                            "2) Release Cart. \t\t\t\t" +
                            "7) Change phone number. \n" +
                            "3) Add product to cart. \t\t" +
                            "8) Checkout. \n" +
                            "4) Remove product from cart. \t" +
                            "9) logout.\n" +
                            "5) Change password.\t\t\t\t" +
                            "10) exit(w/o logout).\n" + "=======");
            selection = s.nextLine();
            try {
                switch (selection) {
                    case "1":
                        if (findAvailableCart(store))
                            System.out.println("Cart added successfully");
                        break;
                    case "2":
                        if (releaseCart())
                            System.out.println("Cart released.");
                        else
                            System.out.println("You dont have a cart.");
                        break;
                    case "3":
                        if (store.displayProducts()) {
                            System.out.println("Insert product ID to add the item to the cart");
                            addProductToCart(store, s.nextLine());
                        }
                        break;
                    case "4":
                        if (!removeProductFromCart())
                            System.out.println("You dont have products in cart.");
                        break;
                    case "5":
                        System.out.println("Password " + (changePassword() ? "changed successfully." : "was not changed."));
                        break;
                    case "6":
                        System.out.println("Address " + (changeAddress() ? "changed successfully." : "was not changed."));
                        break;
                    case "7":
                        System.out.println("Phone " + (changePhoneNumber() ? "changed successfully to - " + getPhoneNumber() : "was not changed."));
                        break;
                    case "8":
                        System.out.println("Your bill:");
                        if (displayBill()) {
                            System.out.println("Do you want to pay? y/n");
                            if (s.nextLine().startsWith("y")) {
                                if (pay())
                                    System.out.println("Payment successful.");
                            }
                        }
                        break;
                    case "9":
                        logout();
                    case "10":
                        selection = "exit";
                        break;
                    default:
                        System.out.println("Selection not recognized, try again.");
                }
            } catch (NumberFormatException numFor) {
                System.out.println("Invalid value of number");
            } catch (NullPointerException nu) {
                System.out.println("You dont own a cart.");

            }
        }

    }


}