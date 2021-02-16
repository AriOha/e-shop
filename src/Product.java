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
    public void setDiscount(int discount) {
        if (0 <= discount && discount <= 100)
            this.discount = discount;
    }

    //    TODO:Exceptions
    public void setPrice(double price) {
        if (price > 0)
            this.price = price;
    }


    void changePrice(double newPrice) {
        setPrice(newPrice);
    }

    void changeDiscount(int newDiscount) {
        setDiscount(newDiscount);
    }

    void displayPrice() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "\nProduct name= " + productName +
                ", Catalog ID= " + catalogId +
                ", Price= " + price +
                ", Discount= " + discount;
    }

}
