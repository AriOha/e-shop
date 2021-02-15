public class Store {

    Product[]  productsList;
    Customer[] customersList;
    Cart[] cartList;
    int currentCarts;

    Store(){
        productsList=new Product[10];
        customersList=new Customer[10];
        cartList=new Cart[10];
    }

    void addCart(){
        if(currentCarts<cartList.length){
            cartList[currentCarts++]= new Cart(10);
        }
    }




}
