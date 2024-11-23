package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.util.Objects;

public class SettingsController implements Observer {
    private ApplicationService applicationService;
    private String username;
    public void setService(ApplicationService service){
        this.applicationService = service;
    }

    public void setUsername(String username){
        this.username = username;
    }
//    public SettingsController(ApplicationService applicationService, String username) {
//        super(applicationService, username);
//    }
    @FXML
    private Button buttonChats;

    @FXML
    private Button buttonFriendsList;

    @FXML
    private Button buttonFriendRequests;

    @FXML
    private Button buttonSettings;

    public void onChatsClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/chats.fxml", new ChatsController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/chats.fxml")));
            Parent newRoot = loader.load();
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonChats.getScene().getWindow();
            currentStage.setScene(scene);
            ChatsController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onFriendsListClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/friendslist.fxml", new FriendsListController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/friendslist.fxml")));
            Parent newRoot = loader.load();
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonFriendsList.getScene().getWindow();
            currentStage.setScene(scene);
            FriendsListController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onFriendRequestsClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/friendrequests.fxml", new FriendRequestsController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/friendrequests.fxml")));
            Parent newRoot = loader.load();
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonFriendRequests.getScene().getWindow();
            currentStage.setScene(scene);
            FriendRequestsController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSettingsClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/settings.fxml", new SettingsController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/settings.fxml")));
            Parent newRoot = loader.load();
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonSettings.getScene().getWindow();
            currentStage.setScene(scene);
            SettingsController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected Button logOut;

    @FXML
    protected void onLogOutClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ubb/scs/socialnetworkgui/views/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) logOut.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onChangeProfilePhotoClick() {
        //switchScene("/ubb/scs/socialnetworkgui/changepassword.fxml", new ChangePassword(friendRequestsService, username));
    }

    @FXML
    private void onChangeInfoClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/changeinfo.fxml", new ChangeInfoController(applicationService, username));
    }

    @FXML
    private void onDeleteAccountClick() {
    }

    @Override
    public void update() {
        // TODO
    }
}
