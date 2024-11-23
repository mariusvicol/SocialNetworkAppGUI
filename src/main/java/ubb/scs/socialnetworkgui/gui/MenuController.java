package ubb.scs.socialnetworkgui.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ubb.scs.socialnetworkgui.domain.FriendRequest;
import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.domain.UserInfo;


public abstract class MenuController {
    protected ApplicationService applicationService;
    protected String username;

    public void setService(ApplicationService service){
        this.applicationService = service;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @FXML
    protected Button buttonProfile;
    @FXML
    protected Button buttonFriendsList;
    @FXML
    protected Button buttonFriendRequests;
    @FXML
    protected Button buttonSettings;
    @FXML
    protected Button searchButton;
    @FXML
    protected TextField searchField;
    @FXML
    protected HBox searchBox;

    private boolean isFriend(String username) {
        for(User friend : applicationService.getFriends(this.username)) {
            if(friend.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void isAllreadyRequest(UserInfo userInfo){
        ImageView imageView = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        Label label = new Label(userInfo.getUsername() + " - " + userInfo.getFirstName() + " " + userInfo.getLastName());
        label.getStyleClass().add("search_label");

        Button add = new Button("");
        ImageView pending = new ImageView("/ubb/scs/socialnetworkgui/images/friendsrequests.png");
        pending.setFitHeight(25);
        pending.setFitWidth(25);
        add.setGraphic(pending);
        add.getStyleClass().add("button_add");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> {
                    searchBox.setVisible(false);
                    searchBox.setManaged(false);
                    searchField.clear();
                })
        );

        add.setOnAction(event -> {
            applicationService.rejectFriendRequest(username, userInfo.getUsername());
            ImageView addImage = new ImageView("/ubb/scs/socialnetworkgui/images/add.png");
            addImage.setFitHeight(25);
            addImage.setFitWidth(25);
            add.setGraphic(addImage);
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        searchBox.setVisible(false);
                        searchBox.setManaged(false);
                        searchField.clear();
                    })
            );
            timeline1.play();
        });

        searchBox.getChildren().addAll(imageView, label, add);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
        timeline.play();
    }

    private void isAllreadyRequestFromTheOtherSide(UserInfo userInfo){
        ImageView imageView = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        Label label = new Label(userInfo.getUsername() + " - " + userInfo.getFirstName() + " " + userInfo.getLastName());
        label.getStyleClass().add("search_label");

        Button add = new Button("");
        ImageView accept = new ImageView("/ubb/scs/socialnetworkgui/images/accept.png");
        accept.setFitHeight(25);
        accept.setFitWidth(25);
        add.setGraphic(accept);
        add.getStyleClass().add("button_add");

        add.setOnAction(event -> {
            applicationService.acceptFriendRequest(userInfo.getUsername(), username);
            searchBox.setManaged(false);
            searchBox.setVisible(false);
        });

        Button reject = new Button("");
        ImageView rejectImage = new ImageView("/ubb/scs/socialnetworkgui/images/decline.png");
        rejectImage.setFitHeight(25);
        rejectImage.setFitWidth(25);
        reject.setGraphic(rejectImage);
        reject.getStyleClass().add("button_add");
        reject.setOnAction(event->{
            applicationService.rejectFriendRequest(userInfo.getUsername(), username);
            searchBox.setManaged(false);
            searchBox.setVisible(false);
        });

        searchBox.getChildren().addAll(imageView, label, add, reject);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
    }

    void isNotFriendRequest(UserInfo userInfo){
        ImageView imageView = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Label label = new Label(userInfo.getUsername() + " - " + userInfo.getFirstName() + " " + userInfo.getLastName());
        label.getStyleClass().add("search_label");

        Button add = new Button("");
        ImageView addImage = new ImageView("/ubb/scs/socialnetworkgui/images/add.png");
        addImage.setFitHeight(25);
        addImage.setFitWidth(25);
        add.setGraphic(addImage);
        add.getStyleClass().add("button_add");
        add.setOnAction(event -> {
            applicationService.sendFriendRequest(username, userInfo.getUsername());

            ImageView pending = new ImageView("/ubb/scs/socialnetworkgui/images/friendsrequests.png");
            pending.setFitHeight(25);
            pending.setFitWidth(25);
            add.setGraphic(pending);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        searchBox.setVisible(false);
                        searchBox.setManaged(false);
                        searchField.clear();
                    })
            );
            timeline.play();
        });

        searchBox.getChildren().addAll(imageView, label, add);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
    }

    private void userNotFound(){
        Label label = new Label("No user found");
        label.getStyleClass().add("error_label");
        searchBox.getChildren().addAll(label);
        searchBox.setVisible(true);
        searchBox.setManaged(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> {
                    searchBox.setVisible(false);
                    searchBox.setManaged(false);
                    searchField.clear();
                })
        );
        timeline.play();
    }

    private void isFriendMessage(UserInfo userInfo){
        ImageView imageView = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        Label label = new Label(userInfo.getUsername() + " - " + userInfo.getFirstName() + " " + userInfo.getLastName());
        label.getStyleClass().add("search_label");

        Button message = new Button("");
        ImageView messageImage = new ImageView("/ubb/scs/socialnetworkgui/images/message.png");
        messageImage.setFitHeight(25);
        messageImage.setFitWidth(25);
        message.setGraphic(messageImage);
        message.getStyleClass().add("button_add");

        //TODO: Add action for sending message

        searchBox.getChildren().addAll(imageView, label, message);
        searchBox.setVisible(true);
        searchBox.setManaged(true);

        ///TODO: Remove after adding sending message
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> {
                    searchBox.setVisible(false);
                    searchBox.setManaged(false);
                    searchField.clear();
                })
        );
        timeline.play();
    }

    @FXML
    protected void onSearchClick() {
        String searchedUser = searchField.getText();
        UserInfo userInfo = applicationService.searchUsersInfo(searchedUser);
        FriendRequest friendRequest = applicationService.getFriendRequest(username, searchedUser);
        FriendRequest friendRequestFromTheOtherSide = applicationService.getFriendRequest(searchedUser, username);
        searchBox.getChildren().clear();

        if(searchedUser.equals("admin")){
            userNotFound();
            return;
        }

        if (friendRequest != null) {
            isAllreadyRequest(userInfo);
            return;
        }
        if (friendRequestFromTheOtherSide != null) {
            isAllreadyRequestFromTheOtherSide(userInfo);
            return;
        }

        if(isFriend(searchedUser)){
            isFriendMessage(userInfo);
        }
        else if (userInfo != null && !userInfo.getUsername().equals(username) && !isFriend(userInfo.getUsername())) {
            isNotFriendRequest(userInfo);
        }
        else {
            userNotFound();
        }
    }

//    protected void switchScene(String fxmlFile, Object controller) {
//        try {
//            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlFile)));
//            loader.setController(controller);
//            Parent newRoot = loader.load();
//            Scene scene = new Scene(newRoot, 1500, 1000);
//            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
//            Stage currentStage = (Stage) buttonProfile.getScene().getWindow();
//            currentStage.setScene(scene);
//            currentStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
