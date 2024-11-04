package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.repository.database.UsersDBInfo;

import java.util.Objects;
import java.util.Optional;

public class Login{
    @FXML
    private Label error;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Button back;

    private final Repository<String, UserInfo> userInfoRepository = new UsersDBInfo("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");


    @FXML
    protected void onLoginClick() {
        String usernameText = username.getText();
        String passwordText = password.getText();
        Optional<UserInfo> userInfo = userInfoRepository.findOne(usernameText);
        if(userInfo.isPresent()) {
            if (userInfo.get().getPassword().equals(passwordText)) {
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/index.fxml")));
                    Scene scene = new Scene(root, 1500, 1000);
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/style.css")).toExternalForm());
                    Stage currentStage = (Stage) login.getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/index.fxml")));
            Scene scene = new Scene(root, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/style.css")).toExternalForm());
            Stage currentStage = (Stage) back.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}