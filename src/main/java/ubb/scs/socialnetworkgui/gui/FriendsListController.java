package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.domain.FriendRequest;
import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.Constants;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FriendsListController implements Observer{
//    public FriendsListController(ApplicationService applicationService, String username) {
//        super(applicationService, username);
//        applicationService.addObserver(this);
//    }

    private ApplicationService applicationService;
    private String username;
    public void setService(ApplicationService service){
        this.applicationService = service;
        applicationService.addObserver(this);
    }

    public void setUsername(String username){
        this.username = username;
        refreshFriendList();
    }

    @FXML
    private Button buttonChats;

    @FXML
    private Button buttonFriendsList;

    @FXML
    private Button buttonFriendRequests;

    @FXML
    private Button buttonSettings;

    @FXML
    protected Button searchButton;
    @FXML
    protected TextField searchField;
    @FXML
    protected VBox search;

    private boolean isFriend(String username) {
        for(User friend : applicationService.getFriends(this.username)) {
            if(friend.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void isAllreadyRequest(UserInfo userInfo){
        HBox searchBox = new HBox();
        searchBox.setSpacing(20);
        searchBox.setAlignment(javafx.geometry.Pos.CENTER);
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

        add.setOnAction(event -> {
            applicationService.rejectFriendRequest(username, userInfo.getUsername());
            ImageView addImage = new ImageView("/ubb/scs/socialnetworkgui/images/add.png");
            addImage.setFitHeight(25);
            addImage.setFitWidth(25);
            add.setGraphic(addImage);
        });

        searchBox.getChildren().addAll(imageView, label, add);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
        search.getChildren().add(searchBox);
        search.setVisible(true);
    }

    private void isAllreadyRequestFromTheOtherSide(UserInfo userInfo){
        HBox searchBox = new HBox();
        searchBox.setSpacing(20);
        searchBox.setAlignment(javafx.geometry.Pos.CENTER);
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
        search.getChildren().add(searchBox);
        search.setVisible(true);
    }

    void isNotFriendRequest(UserInfo userInfo){
        HBox searchBox = new HBox();
        searchBox.setSpacing(20);
        searchBox.setAlignment(javafx.geometry.Pos.CENTER);
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
        });

        searchBox.getChildren().addAll(imageView, label, add);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
        search.getChildren().add(searchBox);
        search.setVisible(true);
    }

    private void userNotFound(){
        HBox searchBox = new HBox();
        searchBox.setSpacing(20);
        searchBox.setAlignment(javafx.geometry.Pos.CENTER);
        Label label = new Label("No user found");
        label.getStyleClass().add("error_label");
        searchBox.getChildren().addAll(label);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
        search.getChildren().add(searchBox);
        search.setVisible(true);
    }

    private void isFriendMessage(UserInfo userInfo){
        HBox searchBox = new HBox();
        searchBox.setSpacing(20);
        searchBox.setAlignment(javafx.geometry.Pos.CENTER);
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
        search.getChildren().add(searchBox);
        search.setVisible(true);
    }

    @FXML
    protected void onSearchClick() {
        String searchedUser = searchField.getText();
        UserInfo userInfo = applicationService.searchUsersInfo(searchedUser);
        FriendRequest friendRequest = applicationService.getFriendRequest(username, searchedUser);
        FriendRequest friendRequestFromTheOtherSide = applicationService.getFriendRequest(searchedUser, username);
        search.getChildren().clear();

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

    private void handleSearch(){
        String usernameFilter = searchField.getText().toLowerCase();
        if(usernameFilter.isEmpty()){
            search.setVisible(false);
            return;
        }
        Iterable<UserInfo> filterUser = applicationService.getUsersInfo();
        List<UserInfo> users = new ArrayList<>();
        filterUser.forEach(userInfo ->{
            if(userInfo.getUsername().startsWith(usernameFilter)){
                users.add(userInfo);
            }
        });

        search.getChildren().clear();

        if(users.isEmpty()){
            userNotFound();
            return;
        }

        for(UserInfo user: users){
            FriendRequest friendRequest = applicationService.getFriendRequest(username, user.getUsername());
            FriendRequest friendRequestFromTheOtherSide = applicationService.getFriendRequest(user.getUsername(), username);
            if(user.getUsername().equals("admin")){
                return;
            }

            if (friendRequest != null) {
                isAllreadyRequest(user);
                return;
            }
            if (friendRequestFromTheOtherSide != null) {
                isAllreadyRequestFromTheOtherSide(user);
                return;
            }

            if(isFriend(user.getUsername())){
                isFriendMessage(user);
            }
            else if (user != null && !user.getUsername().equals(username) && !isFriend(user.getUsername())) {
                isNotFriendRequest(user);
            }
        }
    }

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
    protected VBox friendsList;

    @FXML
    private void initialize(){
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch();
        });
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
            friendsList.getChildren().add(noFriends);
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

                sendMessage.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/currentChat.fxml")));
                        Parent newRoot = loader.load();
                        Scene scene = new Scene(newRoot, 1500, 1000);
                        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
                        Stage currentStage = (Stage) buttonChats.getScene().getWindow();
                        currentStage.setScene(scene);
                        CurrentChatController controller = loader.getController();
                        controller.setService(applicationService);
                        controller.setUsername(username);
                        controller.setFriendUsername(friend.getUsername());
                        currentStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

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
