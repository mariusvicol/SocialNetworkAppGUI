package ubb.scs.socialnetworkgui.utils.observer;

import ubb.scs.socialnetworkgui.utils.events.FriendRequestChangeEvent;
import ubb.scs.socialnetworkgui.utils.events.Event;
import ubb.scs.socialnetworkgui.utils.events.MessageChangeEvent;
import ubb.scs.socialnetworkgui.utils.events.UserChangeEvent;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    List<Observer> observerFriendRequest = new ArrayList<>();
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}