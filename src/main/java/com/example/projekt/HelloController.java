package com.example.projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.paint.Color;


public class HelloController {

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    private Image image;
    private int stage;

    @FXML
    void choose(ActionEvent event) {
        imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if(file != null) {
            String imagepath = file.getPath();
            image = new Image(imagepath);
            imageView1.setImage(image);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
            alert.showAndWait();
        }
        stage=0;
    }

    @FXML
    void next(ActionEvent event) {
        if(stage != -1) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            PixelReader reader = image.getPixelReader();
            WritableImage dest = new WritableImage(width, height);
            PixelWriter writer = dest.getPixelWriter();

            if (stage == 0) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (reader.getColor(x, y).toString().equals("0xffffffff")) {
                            Color c = new Color(0, 0, 0, reader.getColor(x, y).getOpacity());
                            writer.setColor(x, y, c);
                        } else if (reader.getColor(x, y).toString().equals("0x000000ff")) {
                            Color c = new Color(1, 1, 1, reader.getColor(x, y).getOpacity());
                            writer.setColor(x, y, c);
                        }
                    }
                }
            imageView2.setImage(dest);
            stage++;
            }else if(stage == 1){
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (reader.getColor(x, y).toString().equals("0xffffffff")) {
                            Color c = Color.web("0x0000FF");
                            writer.setColor(x, y, c);
                        } else if (reader.getColor(x, y).toString().equals("0x000000ff")) {
                            Color c = Color.web("0xFF0000");
                            writer.setColor(x, y, c);
                        }
                    }
                }
                imageView3.setImage(dest);
                stage = -1;
            }
        }
    }
}
