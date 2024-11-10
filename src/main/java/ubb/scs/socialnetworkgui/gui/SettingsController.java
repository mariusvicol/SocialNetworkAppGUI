package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.awt.event.ActionEvent;
import java.util.Objects;

public class SettingsController extends MenuController implements Observer {

    public SettingsController(ApplicationService applicationService, String username) {
        super(applicationService, username);
    }
    @FXML
    public void onProfileClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/profile.fxml", new ProfileController(applicationService, username));
    }
    @FXML
    public void onFriendsListClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/friendslist.fxml", new FriendsListController(applicationService, username));
    }
    @FXML
    public void onFriendRequestsClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/friendrequests.fxml", new FriendRequestsController(applicationService, username));
    }
    @FXML
    public void onSettingsClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/settings.fxml", new SettingsController(applicationService, username));
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
    private void onChangePasswordClick() {
        //switchScene("/ubb/scs/socialnetworkgui/changepassword.fxml", new ChangePassword(friendRequestsService, username));
    }

    @FXML
    private void onDeleteAccountClick() {
    }

    @Override
    public void update() {
        // TODO
    }
}
