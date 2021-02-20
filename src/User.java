
public class User {

    protected String userName;
    protected String password;
    protected boolean isLoggedIn = false;

    enum Membership {
        Basic, Bronze, Gold, Platinum,Manager
    }

    Membership membership;

    User(String userName, String password) {
        this(userName,password,Membership.Basic);
    }

    User(String userName, String password,Membership membership) {
        setUserName(userName);
        setPassword(password);
        this.membership = membership;
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
    public boolean login(String password) {
        if (isLoggedIn) {
            System.out.println("User Already logged in");
            return false;
        } else if (this.password.equals(password)) {
            this.isLoggedIn = true;
            System.out.println("Hello " + userName + "\t|" + membership + "|");
            return true;
        } else
            System.out.println("Wrong password.");
        return false;
    }

    public boolean login(String UserName, String password) {
        if (UserName.equals(userName))
            return this.login(password);
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
        System.out.println(userName + " logged out.");
    }

    void remoteLogout(String userName, String password) {

    }

}
