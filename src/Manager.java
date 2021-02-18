public class Manager extends User{

    Store managedStore;

    Manager(String userName, String password) {
        super(userName, password);
    }
    Manager(String userName, String password,Store managedStore) {
        this(userName, password);
        this.managedStore = managedStore;
    }



}
