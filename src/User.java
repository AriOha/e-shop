import java.io.PrintWriter;

public class User {

    enum Membership {
        Basic, Bronze, Gold, Platinum, Manager
    }

    protected String userName;
    protected String password;
    protected boolean isLoggedIn = false;
    Membership membership;


    User(String userName, String password) {
        this(userName, password, Membership.Basic);
    }

    User(String userName, String password, Membership membership) {
        setUserName(userName);
        setPassword(password);
        this.membership = membership;
    }

    public void save(PrintWriter pw) {
        pw.println(membership);
        pw.println(userName);
        pw.println(password);
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
        } else if (passwordAuthentication(password)) {
            this.isLoggedIn = true;
            System.out.println("Hello " + userName + "  |" + membership + "|");
            return true;
        } else
            System.out.println("Wrong password.");
        return false;
    }

    public boolean login(String userName, String password) {
        if (this.userName.equals(userName))
            return this.login(password);
        return false;
    }

    public boolean logout() {
        if (isLoggedIn) {
            this.isLoggedIn = false;
            System.out.println(userName + " logged out.");
            return true;
        }
        System.out.println(userName + " not Online.");
        return false;
    }

    boolean remoteLogout(String password) {
        if (passwordAuthentication(password))
            logout();
        else System.out.println("Password incorrect");
        return false;
    }

    boolean userAuthentication(String userName) {
        return (userName.equals(getUserName()));
    }

    boolean passwordAuthentication(String password) {
        return (password.equals(getPassword()));
    }
}
