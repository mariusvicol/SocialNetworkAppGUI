package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.util.Objects;

public class ChatsListController implements Observer {
//    public ChatsListController(ApplicationService applicationService, String username) {
//        super(applicationService, username);
//    }

    private ApplicationService applicationService;
    private String username;
    public void setService(ApplicationService service){
        this.applicationService = service;
        applicationService.addObserver(this);
    }

    public void setUsername(String username){
        this.username = username;
    }

    @FXML
    private Button buttonProfile;

    @FXML
    private Button buttonUsersList;

    @FXML
    private Button buttonChatsList;

    @FXML
    private Button buttonSettings;

    @FXML
    public void onProfileClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/chats.fxml", new ProfileController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/chats.fxml")));
            Parent newRoot = loader.load();
            ChatsController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonProfile.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onUsersListClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/userslist.fxml", new UsersListController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/userslist.fxml")));
            Parent newRoot = loader.load();
            UsersListController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonUsersList.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onChatsListClick() {
        //switchScene("/ubb/scs/socialnetworkgui/views/chatslist.fxml", new ChatsListController(applicationService, username));
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/views/chatlists.fxml")));
            Parent newRoot = loader.load();
            ChatsListController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonChatsList.getScene().getWindow();
            currentStage.setScene(scene);
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
            SettingsController controller = loader.getController();
            controller.setService(applicationService);
            controller.setUsername(username);
            Scene scene = new Scene(newRoot, 1500, 1000);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
            Stage currentStage = (Stage) buttonSettings.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize(){
    }

    @Override
    public void update() {
        // TODO
    }
}
