package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import ubb.scs.socialnetworkgui.domain.FriendRequest;
import ubb.scs.socialnetworkgui.domain.Message;
import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.Notifications;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrentChatController implements Observer {
    @FXML
    private Label usernameLabel;

    @FXML
    private Image imageLogo;

    private ApplicationService applicationService;
    private String username;
    private String friendUsername;
    public void setService(ApplicationService service){
        this.applicationService = service;
        applicationService.addObserver(this);
        System.out.println("CurrentChatController setService");
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setFriendUsername(String friendUsername){
        this.friendUsername = friendUsername;
        refreshMessage();
        usernameLabel.setText(friendUsername);
    }

    @FXML
    private ScrollPane scrollPane;
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
        search.getChildren().add(searchBox);
        search.setVisible(true);
        search.toFront();
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
            searchBox.setVisible(false);
        });

        searchBox.getChildren().addAll(imageView, label, add, reject);
        searchBox.setVisible(true);
        search.getChildren().add(searchBox);
        search.setVisible(true);
        search.toFront();
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
            if(applicationService.isOnline(userInfo.getUsername())){
                Notifications.showNotification(username);
            }
        });

        searchBox.getChildren().addAll(imageView, label, add);
        searchBox.setVisible(true);
        search.getChildren().add(searchBox);
        search.setVisible(true);
        search.toFront();
    }

    private void userNotFound(){
        HBox searchBox = new HBox();
        searchBox.setSpacing(20);
        searchBox.setAlignment(javafx.geometry.Pos.CENTER);
        Label label = new Label("No user found");
        label.getStyleClass().add("error_label");
        searchBox.getChildren().addAll(label);
        searchBox.setVisible(true);
        search.getChildren().add(searchBox);
        search.setVisible(true);
        search.toFront();
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
        search.getChildren().add(searchBox);
        search.setVisible(true);
        search.toFront();
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

    private void handleSearch() {
        String usernameFilter = searchField.getText().toLowerCase();
        if (usernameFilter.isEmpty()) {
            search.setVisible(false);
            return;
        }
        Iterable<UserInfo> filterUser = applicationService.getUsersInfo();
        List<UserInfo> users = new ArrayList<>();
        filterUser.forEach(userInfo -> {
            if (userInfo.getUsername().startsWith(usernameFilter) && !userInfo.getUsername().equals(username)) {
                users.add(userInfo);
            }
        });

        search.getChildren().clear();

        if (users.isEmpty()) {
            userNotFound();
            return;
        }

        int counter = 0;
        for (UserInfo user : users) {
            if (counter >= 5) {
                break;
            }

            FriendRequest friendRequest = applicationService.getFriendRequest(username, user.getUsername());
            FriendRequest friendRequestFromTheOtherSide = applicationService.getFriendRequest(user.getUsername(), username);

            if (user.getUsername().equals("admin")) {
                continue;
            }

            if (friendRequest != null) {
                isAllreadyRequest(user);
                counter++;
                continue;
            }
            if (friendRequestFromTheOtherSide != null) {
                isAllreadyRequestFromTheOtherSide(user);
                counter++;
                continue;
            }

            if (isFriend(user.getUsername())) {
                isFriendMessage(user);
            } else if (!user.getUsername().equals(username) && !isFriend(user.getUsername())) {
                isNotFriendRequest(user);
            }
            counter++;
        }
    }

    @FXML
    private void initialize(){
        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

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
    protected VBox messagesList;

    private void refreshMessage() {
        messagesList.getChildren().clear();
        List<Message> messages = applicationService.getMessage(username, friendUsername);

        for (Message message : messages) {
            HBox messageBox = new HBox();
            messageBox.setSpacing(10);

            Text messageLabel= new Text(message.getContent());
            messageLabel.getStyleClass().add("message_label");
            messageLabel.setWrappingWidth(300);
            messageLabel.setTextAlignment(TextAlignment.LEFT);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            if (message.getSender().equals(username)) {
                ImageView imageViewSender;
                try{
                    imageViewSender = new ImageView("/ubb/scs/socialnetworkgui/users_logo/" + username.charAt(0) + ".png");
                    imageViewSender.setFitWidth(40);
                    imageViewSender.setFitHeight(40);
                } catch (Exception e){
                    imageViewSender = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
                    imageViewSender.setFitWidth(40);
                    imageViewSender.setFitHeight(40);
                }
                messageLabel.getStyleClass().add("user_message");
                messageBox.getChildren().addAll(spacer, messageLabel, imageViewSender);
                messageBox.setAlignment(Pos.CENTER_RIGHT);
            } else {
                ImageView imageViewReceiver;
                try{
                    imageViewReceiver = new ImageView("/ubb/scs/socialnetworkgui/users_logo/" + friendUsername.charAt(0) + ".png");
                    imageViewReceiver.setFitWidth(40);
                    imageViewReceiver.setFitHeight(40);
                } catch (Exception e){
                    imageViewReceiver = new ImageView("/ubb/scs/socialnetworkgui/users_logo/usernologo.png");
                    imageViewReceiver.setFitWidth(40);
                    imageViewReceiver.setFitHeight(40);
                }
                messageLabel.getStyleClass().add("friend_message");
                messageBox.getChildren().addAll(imageViewReceiver, messageLabel, spacer);
                messageBox.setAlignment(Pos.CENTER_LEFT);
            }

            messagesList.getChildren().add(messageBox);
        }
        messagesList.setVisible(true);
        scrollPane.setVvalue(1.0);
    }

    @FXML
    protected TextField message;

    @FXML
    protected Button buttonSend;

    @FXML
    protected void onSendClick() {
        String messageString  = message.getText();
        Message messageSend = new Message(username, friendUsername, messageString, LocalDateTime.now());
        applicationService.sendMessage(messageSend);
        message.clear();
    }

    @Override
    public void update() {
        System.out.println("Update Message");
        refreshMessage();
    }
}

