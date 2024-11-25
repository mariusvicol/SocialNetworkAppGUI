package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.domain.*;
import ubb.scs.socialnetworkgui.domain.validators.FriendshipValidator;
import ubb.scs.socialnetworkgui.domain.validators.UserValidator;
import ubb.scs.socialnetworkgui.domain.validators.ValidationException;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.repository.database.*;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.service.UserInfoServiceGUI;

import java.util.Objects;

public class SignUpController {
    @FXML
    private Label error;
    @FXML
    private Button submit;
    @FXML
    private Button back;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm_password;

    private final Repository<String, UserInfo> usersInfoRepository = new UsersInfoDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final Repository<Tuple<Long, Long>, Friendship> friendshipsRepository = new FriendshipsDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "0806", new FriendshipValidator());
    private final Repository<Long, User> usersRepository = new UsersDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "0806", new UserValidator());
    private final Repository<Tuple<String,String>, FriendRequest> friendRequestsRepository = new FriendRequestsDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final Repository<Integer, Message> messageRepository = new MessageDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final Repository<String,Sessions> sessionsRepository = new SessionsDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final ApplicationService applicationService = new ApplicationService(usersRepository, friendshipsRepository, usersInfoRepository, friendRequestsRepository, messageRepository, sessionsRepository);
    @FXML
    protected void onSubmitClick() {
        String first_nameText = first_name.getText();
        String last_nameText = last_name.getText();
        String usernameText = username.getText();
        String passwordText = password.getText();
        String passwordConfirmText = confirm_password.getText();
        if(!passwordConfirmText.equals(passwordText)){
            error.setText("Password doesn't match!");
            password.clear();
            confirm_password.clear();
            return;
        }
        UserInfo userInfo = applicationService.findOneService(usernameText).orElse(null);
        if (userInfo != null) {
            error.setText("Username already exists!");
            return;
        }
        try{
            applicationService.addUserInfo(usernameText, first_nameText, last_nameText, passwordText);
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/login.fxml")));
                Scene scene = new Scene(root, 1500, 1000);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
                Stage currentStage = (Stage) submit.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ValidationException e) {
            error.setText(e.getMessage());
        }
    }

    @FXML
    protected void onBackClick() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/index.fxml")));
            Scene scene = new Scene(root, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) back.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}