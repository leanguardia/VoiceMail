package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deleguard on 4/10/16.
 */
public interface Subject {

    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void updateObservers();
}
