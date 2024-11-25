package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ubb.scs.socialnetworkgui.service.ApplicationService;

import java.io.File;
import java.io.IOException;

public class ChangePhotoController {

    private ApplicationService applicationService;
    private String username;

    public void setService(ApplicationService service){
        this.applicationService = service;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @FXML
    private ImageView imageView;

    @FXML
    private void browseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                saveImage(selectedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImage(File imageFile) {
        File destinationFile = new File("path_to_project/" + "username.jpg");
        try {
            java.nio.file.Files.copy(imageFile.toPath(), destinationFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        imageView.setOnDragOver(event -> {
            if (event.getGestureSource() != imageView && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
        });

        imageView.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                File file = event.getDragboard().getFiles().get(0);
                try {
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    saveImage(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                event.setDropCompleted(true);
            }
            event.consume();
        });
    }
}