package ubb.scs.socialnetworkgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class SocialNetworkApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SocialNetworkApp.class.getResource("index.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 1500, 1000);
        loginScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Social Network");
        stage.setScene(loginScene);
        stage.sizeToScene();

        Image logo = new Image("https://www.shutterstock.com/image-vector/support-icon-can-be-used-600nw-1887496465.jpg");
        stage.getIcons().add(logo);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}