package ubb.scs.socialnetworkgui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class SocialNetworkApp extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(SocialNetworkApp.class.getResource("/ubb/scs/socialnetworkgui/views/index.fxml"));
//        Scene loginScene = new Scene(fxmlLoader.load(), 1500, 1000);
//        loginScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());
//        stage.setTitle("Social Network");
//        stage.setScene(loginScene);
//        stage.sizeToScene();
//        Image logo = new Image("/ubb/scs/socialnetworkgui/images/logo.png");
//        stage.getIcons().add(logo);
//        stage.show();
//    }
//    public static void main(String[] args) {
//        launch();
//    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Deschidem prima fereastră
        openWindow(primaryStage, "/ubb/scs/socialnetworkgui/views/index.fxml", "Social Network");

        // Deschidem a doua fereastră
        Stage secondaryStage = new Stage();
        openWindow(secondaryStage, "/ubb/scs/socialnetworkgui/views/index.fxml", "Social Network - Second Window");
    }

    private void openWindow(Stage stage, String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SocialNetworkApp.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 1000);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ubb/scs/socialnetworkgui/css/style.css")).toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.sizeToScene();

        Image logo = new Image("/ubb/scs/socialnetworkgui/images/sociallogo.jpg");
        stage.getIcons().add(logo);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
