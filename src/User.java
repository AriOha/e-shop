import Interfaces.Loggable;

public class User implements Loggable {

    protected String userName;
    protected String password;
    protected boolean isLoggedIn = false;


    User(String userName, String password){
        setUserName(userName);
        setPassword(password);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //    TODO: Exceptions
    public void login(String userName, String password) {
        if (this.userName.equals(userName)) {
            if (this.password.equals(password)) {
                this.isLoggedIn = true;
                System.out.println("Welcome " + userName + ".");
            } else
                System.out.println("Wrong password.");

        } else
            System.out.println("Wrong username.");
    }
    public void logout() {
        this.isLoggedIn = false;
        System.out.println("Goodbye " + userName + ".");
    }

}
