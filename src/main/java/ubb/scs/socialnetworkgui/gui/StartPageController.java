package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class StartPageController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button login;

    @FXML
    private Button signUp;

    @FXML
    private void switchScene(String path) {
        try {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
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
        switchScene("/ubb/scs/socialnetworkgui/views/login.fxml");
    }

    @FXML
    protected void onSignUpClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/signup.fxml");
    }

}