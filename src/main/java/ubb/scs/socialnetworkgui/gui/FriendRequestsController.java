package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.Constants;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

public class FriendRequestsController extends MenuController implements Observer {
    public FriendRequestsController(ApplicationService applicationService, String username) {
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
    protected VBox friendRequestsList;

    @FXML
    private void initialize() {
        refreshFriendRequests();
    }

    @FXML
    private void refreshFriendRequests() {
        int size = applicationService.showAllFriendRequestsUser(username).size();
        friendRequestsList.getChildren().clear();
        if(size == 0){
            Label label = new Label("No friend requests");
            label.getStyleClass().add("search_label");

            friendRequestsList.getChildren().add(label);
        }
        else {
            applicationService.showAllFriendRequestsUser(username).forEach(friendRequest -> {
                HBox friendRequestBox = new HBox();
                friendRequestBox.setSpacing(5);
                friendRequestBox.setAlignment(javafx.geometry.Pos.CENTER);

                ImageView imageView = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);

                UserInfo userInfo = applicationService.searchUsersInfo(friendRequest.getFrom());

                Label label = new Label(userInfo.getUsername() + " - " + userInfo.getLastName() + " " + userInfo.getFirstName());
                label.getStyleClass().add("search_label");

                Label hoverLabel = new Label("Sent at: " + friendRequest.getDateTime().format(Constants.DATE_TIME_FORMATTER));
                hoverLabel.setVisible(false);

                label.setOnMouseEntered(event -> hoverLabel.setVisible(true));
                label.setOnMouseExited(event -> hoverLabel.setVisible(false));


                Button accept = new Button("");
                ImageView imageView0 = new ImageView("/ubb/scs/socialnetworkgui/images/accept.png");
                imageView0.setFitHeight(25);
                imageView0.setFitWidth(25);
                accept.setGraphic(imageView0);
                accept.getStyleClass().add("button_add");
                accept.setOnAction(event -> {
                    applicationService.acceptFriendRequest(friendRequest.getFrom(), username);
                });

                Button decline = new Button("");
                ImageView imageView1 = new ImageView("/ubb/scs/socialnetworkgui/images/decline.png");
                imageView1.setFitHeight(25);
                imageView1.setFitWidth(25);
                decline.setGraphic(imageView1);
                decline.getStyleClass().add("button_add");
                decline.setOnAction(event -> {
                    applicationService.rejectFriendRequest(friendRequest.getFrom(), username);
                });
                friendRequestBox.getChildren().addAll(imageView, label, accept, decline);
                VBox friendRequestInfo = new VBox();
                friendRequestInfo.setAlignment(javafx.geometry.Pos.CENTER);
                friendRequestInfo.getChildren().addAll(friendRequestBox, hoverLabel);
                friendRequestsList.getChildren().add(friendRequestInfo);
            });
        }
        friendRequestsList.setVisible(true);
    }


    @Override
    public void update() {
        System.out.println("Friend requests updated");
        refreshFriendRequests();
    }
}
