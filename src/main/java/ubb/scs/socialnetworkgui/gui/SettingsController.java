package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    @FXML
    private Button buttonChats;

    @FXML
    private Button buttonFriendsList;

    @FXML
    private Button buttonFriendRequests;

    @FXML
    private Button buttonSettings;

    public void onChatsClick() {
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
            applicationService.removeSession(username);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onChangeProfilePhotoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/changephoto.fxml")));
            Parent newRoot = loader.load();
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonFriendRequests.getScene().getWindow();
            currentStage.setScene(scene);
            ChangePhotoController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onChangeInfoClick() {
        /// TODO
    }

    private void deleteAlert(){
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete Account");
        deleteAlert.setHeaderText("Are you sure you want to delete your account?");
        deleteAlert.setContentText("This action cannot be undone.");
        deleteAlert.showAndWait();
        if (deleteAlert.getResult().getButtonData().isCancelButton()) {
            deleteAlert.close();
        } else {
            applicationService.deleteUser(username);
        }
    }

    @FXML
    private void onDeleteAccountClick() {
//        deleteAlert();
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ubb/scs/socialnetworkgui/views/login.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root, 1500, 1000);
//            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
//            Stage currentStage = (Stage) logOut.getScene().getWindow();
//            currentStage.setScene(scene);
//            currentStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void update() {
        // TODO
    }
}
