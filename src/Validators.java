public class Validators {

    static boolean validRange(int number, int min, int max) {
        return (min <= number && number <= max);
    }

    static boolean validUserName(String username, Store store) {
        if (validRange(username.length(), 3, 10) && !Character.isDigit(username.charAt(0)) && !username.contains(" "))
            if (store.searchForCustomer(username) == null)
                return true;
            else {
                System.out.println("Username already exists.");
            }
        else {
            System.out.println("Username not valid.");
        }
        return false;
    }


    static boolean validPassword(String password) {
        if (validRange(password.length(), 5, 15))
            return true;
        System.out.println("Password not valid.");
        return false;
    }

    static boolean validAddress(String address) {
        if (validRange(address.length(), 5, 30))
            return true;
        System.out.println("Address not valid.");
        return false;
    }

    static boolean phoneValidator(String phone) {

        for (int i = 0; i < phone.length(); i++)
            if (phone.charAt(i) < '0' || phone.charAt(i) > '9') {
                System.out.println("Phone not valid.");
                return false;
            }
        return true;
    }

    static boolean validProductName(String productName, Store store) {
        if (validRange(productName.length(), 3, 20)) {
            for (Product product : store.productsList) {
                if (product.getProductName().equals(productName)) {
                    System.out.println("Product name already exists.");
                    return false;
                }
            }
        } else {
            System.out.println("Product name not valid.");
            return false;
        }
        return true;
    }
}
