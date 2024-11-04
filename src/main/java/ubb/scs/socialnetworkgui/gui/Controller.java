package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.repository.database.UsersDBInfo;

import java.util.Objects;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    private Button login;

    @FXML
    private Button signUp;

    @FXML
    protected void onLoginClick() {
        welcomeText.setText("Friend added!");
        try{
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/login.fxml")));
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/style.css")).toExternalForm());
            Stage currentStage = (Stage) login.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSignUpClick() {
        try{
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/signup.fxml")));
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/style.css")).toExternalForm());
            Stage currentStage = (Stage) signUp.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}