public class Product {

    static int startID = 1000;
    private String productName;
    protected int catalogId;
    private double price;
    private int discount;

    Product(){
        catalogId=startID++;
    }

    void changeUnitPrice(double newPrice){
    }
    void changeDiscount(double newPrice){
    }

    @Override
    public String toString() {
        return "Product name= " + productName +
                ", Catalog ID= " + catalogId +
                ", Price= " + price +
                ", Discount= " + discount;
    }
}
