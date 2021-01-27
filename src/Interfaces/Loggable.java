package Interfaces;

public interface Loggable {
    String userName = null;
    public void login(String userName, String password);
    public void logout();
}
