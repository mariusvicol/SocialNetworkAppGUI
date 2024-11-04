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
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.repository.database.UsersDBInfo;

import java.util.Objects;
import java.util.Optional;

public class SignUp{
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

    private final Repository<String, UserInfo> userInfoRepository = new UsersDBInfo("jdbc:postgresql://localhost:5432/usersinfo", "postgres", "0806");

    @FXML
    protected void onSubmitClick() {
        String first_nameText = first_name.getText();
        String last_nameText = last_name.getText();
        String usernameText = username.getText();
        String passwordText = password.getText();
        UserInfo userInfo = new UserInfo(first_nameText, last_nameText, passwordText);
        userInfo.setId(usernameText);
        Optional<UserInfo> find = userInfoRepository.save(userInfo);
        if(find.isPresent()){
            error.setText("User already exists!");
            username.clear();
        }
        else{
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/login.fxml")));
                Scene scene = new Scene(root, 1500, 1000);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/style.css")).toExternalForm());
                Stage currentStage = (Stage) submit.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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