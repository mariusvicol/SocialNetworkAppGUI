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
import ubb.scs.socialnetworkgui.utils.Constants;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.util.Objects;
import java.util.Optional;

public class FriendsListController extends MenuController implements Observer{
    public FriendsListController(ApplicationService applicationService, String username) {
        super(applicationService, username);
        applicationService.addObserver(this);
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
    protected VBox friendsList;

    @FXML
    private void initialize(){
        refreshFriendList();
    }

    @FXML
    private void deleteFriendAlert(String username, String friendUsername){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete friend");
        alert.setHeaderText("Are you sure you want to delete this friend?");
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            applicationService.removeFriendship(username, friendUsername);
        } else {
            alert.close();
        }
    }

    @FXML
    private void refreshFriendList(){
        System.out.println("Refreshing friends list");
        friendsList.getChildren().clear();
        if(applicationService.getFriends(username).isEmpty()){
            Label noFriends = new Label("No friends yet");
            noFriends.getStyleClass().add("label_title");
        }
        else {
            applicationService.getFriends(username).forEach(friend -> {
                HBox friendBox = new HBox();
                friendBox.setSpacing(5);
                friendBox.setAlignment(javafx.geometry.Pos.CENTER);

                ImageView imageView = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);

                Label friendName = new Label(friend.getUsername() +" - "+friend.getLastName() + " " + friend.getFirstName());
                friendName.getStyleClass().add("search_label");

                Label hoverLabel = new Label("Friends since: " + applicationService.getFriendshipDate(username, friend.getUsername()).format(Constants.DATE_TIME_FORMATTER));
                hoverLabel.setVisible(false);

                friendName.setOnMouseEntered(event -> hoverLabel.setVisible(true));
                friendName.setOnMouseExited(event -> hoverLabel.setVisible(false));

                ///TODO: Change css class

                Button sendMessage = new Button("");
                ImageView message = new ImageView("/ubb/scs/socialnetworkgui/images/message.png");
                message.setFitHeight(25);
                message.setFitWidth(25);
                sendMessage.setGraphic(message);
                sendMessage.getStyleClass().add("button_add");

                /// TODO: Add action for sending message

                Button removeFriend = new Button("");
                ImageView remove = new ImageView("/ubb/scs/socialnetworkgui/images/delete.png");
                remove.setFitHeight(25);
                remove.setFitWidth(25);
                removeFriend.setGraphic(remove);
                removeFriend.getStyleClass().add("button_add");
                removeFriend.setOnAction(event -> {
                    deleteFriendAlert(username, friend.getUsername());
                });
                friendBox.getChildren().addAll(imageView, friendName, sendMessage, removeFriend);
                VBox friendInfo = new VBox();
                friendInfo.setAlignment(javafx.geometry.Pos.CENTER);
                friendInfo.getChildren().addAll(friendBox, hoverLabel);
                friendsList.getChildren().add(friendInfo);
            });
        }
        friendsList.setVisible(true);
    }

    @Override
    public void update() {
        System.out.println("Friends list updated");
        refreshFriendList();
    }
}
