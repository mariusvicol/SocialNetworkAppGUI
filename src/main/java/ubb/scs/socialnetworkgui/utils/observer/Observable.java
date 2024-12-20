package ubb.scs.socialnetworkgui.utils.observer;


import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    List<Observer> observerFriendRequest = new ArrayList<>();
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}