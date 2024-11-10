package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.util.Objects;
import java.util.Optional;

public class UsersListController extends MenuController implements Observer {
    public UsersListController(ApplicationService applicationService, String username) {
        super(applicationService, username);
        if (!applicationService.getObservers().contains(this)) {
            applicationService.addObserver(this);
        }
    }

    @FXML
    public void onProfileClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/profile.fxml", new ProfileController(applicationService, username));
    }
    @FXML
    public void onUsersListClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/userslist.fxml", new UsersListController(applicationService, username));
    }
    @FXML
    public void onChatsListClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/chatslist.fxml", new ChatsListController(applicationService, username));
    }
    @FXML
    public void onSettingsClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/settings.fxml", new SettingsController(applicationService, username));
    }

    @FXML
    protected VBox usersList;

    @FXML
    private void initialize() {
        refreshUsersList();
    }

    private void refreshUsersList() {
        usersList.getChildren().clear();
        applicationService.getUsers().forEach(user -> {
            if(user.getUsername().equals("admin")) return;
            HBox userBox = new HBox();
            userBox.setSpacing(5);
            userBox.setAlignment(javafx.geometry.Pos.CENTER);

            ImageView userLogo = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
            userLogo.setFitHeight(40);
            userLogo.setFitWidth(40);

            Label usernameLabel = new Label(user.getUsername() + " - " + user.getLastName()+ " " + user.getFirstName());
            usernameLabel.getStyleClass().add("search_label");

            Button banButton = new Button("");
            ImageView banIcon = new ImageView("/ubb/scs/socialnetworkgui/images/ban.png");
            banIcon.setFitHeight(20);
            banIcon.setFitWidth(20);
            banButton.setGraphic(banIcon);
            banButton.getStyleClass().add("button_add");

            Button deleteButton = new Button("");
            ImageView deleteIcon = new ImageView("/ubb/scs/socialnetworkgui/images/deleteadmin.png");
            deleteIcon.setFitHeight(20);
            deleteIcon.setFitWidth(20);
            deleteButton.setGraphic(deleteIcon);
            deleteButton.getStyleClass().add("button_add");
            deleteButton.setOnAction(event -> {
                deleteAlert(user.getUsername());
            });

            userBox.getChildren().addAll(userLogo, usernameLabel, banButton, deleteButton);
            usersList.getChildren().add(userBox);
        });
        usersList.setVisible(true);
    }

    private void deleteAlert(String username) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete user");
        alert.setHeaderText("Are you sure you want to delete this user?");
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            applicationService.deleteUser(username);
        } else {
            alert.close();
        }
    }

    @Override
    public void update() {
        refreshUsersList();
    }
}
