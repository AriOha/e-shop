public class Cart {

    static int startID = 1000;
    Product[] productsList;
    private final int cartId;
    private int productsCounter;
    Customer ownedByCustomer;


    Cart(){
        this(100);
    }

    Cart(int maxCarts){
        this.productsList = new Product[maxCarts];
        ownedByCustomer =null;
        this.cartId=startID++;
        this.productsCounter=0;
    }

    public int getCartId() {
        return cartId;
    }

    public Customer getOwnedByCustomer() {
        return ownedByCustomer;
    }

    public int getProductsCounter() {
        return productsCounter;
    }

    void addItem(Product p){
        //TODO:
        productsList[productsCounter++]=p;
    }

}
