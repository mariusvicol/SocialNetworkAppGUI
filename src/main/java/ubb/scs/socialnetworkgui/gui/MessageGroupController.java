package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ubb.scs.socialnetworkgui.domain.MessageGroup;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.List;

public class MessageGroupController implements Observer {
    private ApplicationService applicationService;
    private String username;
    private List<String> friendsUsernames;

    @FXML
    private TextField groupMessage;

    @FXML
    protected VBox groupMessageList;

    @FXML
    protected ScrollPane scrollPane;

    public void setService(ApplicationService applicationService) {
        this.applicationService = applicationService;
        applicationService.addObserver(this);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setFriendsUsernames(List<String> friendsUsernames){
        this.friendsUsernames = friendsUsernames;
    }

    @FXML
    private void onSendToGroupClick() {
        String messageContent = groupMessage.getText();
        if (messageContent.isEmpty()) return;

        MessageGroup messageGroup = new MessageGroup(username, friendsUsernames, messageContent, LocalDateTime.now());
        applicationService.sendMessageGroup(messageGroup);
        groupMessage.clear();
    }

    public void refreshGroupMessageList(){
        groupMessageList.getChildren().clear();
        List<MessageGroup> messages = applicationService.getMessageGroup(username);

        for (MessageGroup message : messages) {
            HBox messageBox = new HBox();
            messageBox.setSpacing(10);

            Text messageLabel= new Text(message.getMessage());
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
                    imageViewReceiver = new ImageView("/ubb/scs/socialnetworkgui/users_logo/" + message.getSender().charAt(0) + ".png");
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

            groupMessageList.getChildren().add(messageBox);
        }
        groupMessageList.setVisible(true);
        scrollPane.setVvalue(1.0);
    }

    @Override
    public void update() {
        refreshGroupMessageList();
    }
}
