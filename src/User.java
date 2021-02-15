

public class User {

    protected String userName;
    protected String password;
    protected boolean isLoggedIn = false;


    User(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    //    TODO: Exceptions
    public boolean login(User user) {
        if (isLoggedIn) {
            System.out.println("User Already logged in");
            return false;
        } else if (this.userName.equals(user.userName)) {
            if (this.password.equals(user.password)) {
                this.isLoggedIn = true;
                System.out.println("Welcome " + userName + ".");
            } else
                System.out.println("Wrong password.");
        } else
            System.out.println("Wrong username.");
        return isLoggedIn;
    }

    public void logout() {
        this.isLoggedIn = false;
        System.out.println("Goodbye " + userName + ".");
    }

}
