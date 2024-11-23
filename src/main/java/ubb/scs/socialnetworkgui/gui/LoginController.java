package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.domain.*;
import ubb.scs.socialnetworkgui.domain.validators.FriendshipValidator;
import ubb.scs.socialnetworkgui.domain.validators.UserValidator;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.repository.database.*;
import ubb.scs.socialnetworkgui.service.ApplicationService;

import java.util.Objects;
import java.util.Optional;

/// TODO: implement all users

public class LoginController {
    @FXML
    private Label error;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    public Button login;
    @FXML
    private Button back;

    private final Repository<String, UserInfo> usersInfoRepository = new UsersInfoDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final Repository<Tuple<Long, Long>, Friendship> friendshipsRepository = new FriendshipsDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "0806", new FriendshipValidator());
    private final Repository<Long, User> usersRepository = new UsersDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "0806", new UserValidator());
    private final Repository<Tuple<String,String>, FriendRequest> friendRequestsRepository = new FriendRequestsDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final Repository<Integer, Message> messageRepository = new MessageDBRepository("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");
    private final ApplicationService applicationService = new ApplicationService(usersRepository, friendshipsRepository, usersInfoRepository, friendRequestsRepository, messageRepository);

    protected void switchScene(String fxmlFile, ApplicationService service, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            Objects controller = loader.getController();
            Parent newRoot = loader.load();
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) login.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLoginClick() {
        String usernameText = username.getText();
        String passwordText = password.getText();
        Optional<UserInfo> userInfo = applicationService.findOneService(usernameText);
        if(userInfo.isPresent()) {
            if (userInfo.get().getPassword().equals(passwordText)) {
                if (usernameText.equals("admin")) {
                    //switchScene("/ubb/scs/socialnetworkgui/views/adminpage.fxml", new AdminPageController(applicationService, usernameText));
                    try {
                        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/adminpage.fxml")));
                        Parent newRoot = loader.load();
                        Scene scene = new Scene(newRoot, 1500, 1000);
                        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
                        Stage currentStage = (Stage) login.getScene().getWindow();
                        currentStage.setScene(scene);
                        AdminPageController controller = loader.getController();
                        controller.setService(applicationService);
                        controller.setUsername(usernameText);
                        currentStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //switchScene("/ubb/scs/socialnetworkgui/views/userpage.fxml", new UserPageController(applicationService, usernameText));
                    try {
                        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/userpage.fxml")));
                        Parent newRoot = loader.load();
                        Scene scene = new Scene(newRoot, 1500, 1000);
                        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
                        Stage currentStage = (Stage) login.getScene().getWindow();
                        currentStage.setScene(scene);
                        UserPageController controller = loader.getController();
                        controller.setService(applicationService);
                        controller.setUsername(usernameText);
                        currentStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                error.setText("Invalid password!");
                password.clear();
            }
        }
        else {
            error.setText("User not found!");
            username.clear();
            password.clear();
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