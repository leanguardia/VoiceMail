package UserInterfaces;

import BusinessLogic.Connection;

public interface UserInterface {
    public void run();
    public void speak(String s);
    public void setConnection(Connection c);
}
