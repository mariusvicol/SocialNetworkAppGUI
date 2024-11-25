package ubb.scs.socialnetworkgui.utils;

import javafx.scene.control.Alert;

public class Notifications {
    public static void showNotification(String username) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle("New Friend Request");
        notification.setHeaderText("You have received a friend request!");
        notification.setContentText("User " + username + " has sent you a friend request.");
        notification.show();
    }
}
