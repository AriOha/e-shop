import java.io.PrintWriter;
import java.util.Scanner;

public class Product {

    static int startID = 1000;
    private String productName;
    protected int catalogId;
    private double price;
    private int discount;

    Product(String productName, double price, int discount) {
        setProductName(productName);
        catalogId = startID++;
        setPrice(price);
        setDiscount(discount);
    }

    Product(Scanner s) {
        this(s.next(), s.nextDouble(), s.nextInt());
    }

    public void save(PrintWriter pw) {
        pw.println(productName);
        pw.println(price);
        pw.println(discount);
    }

    public String getProductName() {
        return productName;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //    TODO:Exceptions
    boolean setDiscount(int discount) {
        if (Validators.validRange(discount,0,50)){
            this.discount = discount;
            return true;
        }
        return false;
    }

    //    TODO:Exceptions
    public boolean setPrice(double price) {
        if (price > 0) {
            this.price = price;
            return true;
        }
        return false;
    }


    boolean changePrice(double newPrice) {
        return setPrice(newPrice);
    }

    boolean changeDiscount(int newDiscount) {
        return setDiscount(newDiscount);
    }

    void displayPrice() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "ID: " + catalogId + "\t" + productName + ",\tPrice: " + price + ", <-> " + discount + "% Discount";
    }

}
