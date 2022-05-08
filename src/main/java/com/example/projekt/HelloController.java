package com.example.projekt;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.util.Callback;


public class HelloController {

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private TableView<String[]> table1;

    @FXML
    private TableView<String[]> table2;

    private Image image;
    private int stage;
    private int width;
    private int height;
    private int[][] binary;
    private int[][] labels;
    private int tablicaSklejenRozmiar;
    private int[][] tablicaSklejen;
    private int[][] tablicaSklejen2;
    private String[] colour;
    private int A, B, C, D;
    private int L;
    private int numerOfLabels;
    private PixelReader reader;
    private PixelReader reader2;
    private WritableImage dest;
    private WritableImage dest2;
    private PixelWriter writer;
    private PixelWriter writer2;

    @FXML
    private Button exitLabel;

    @FXML
    private Button nextLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> imageMenuEN;
    @FXML
    private ComboBox<String> imageMenuPL;
    @FXML
    private ComboBox<String> languageMenuPLAction;
    @FXML
    private ComboBox<String> languageMenuENAction;

    @FXML
    private Label titleLabel1;
    @FXML
    private Label titleLabel2;
    @FXML
    private Label titleLabel3;
    @FXML
    private Label firstStage;
    @FXML
    private Label secondStage;

    @FXML
    void initialize(){
        imageMenuEN.getItems().add("Shapes");
        imageMenuEN.getItems().add("Curves");
        imageMenuEN.getItems().add("Handel example");
        imageMenuEN.getItems().add("Logo");
        imageMenuEN.getItems().add("Lines");
        imageMenuEN.getItems().add("Example 1");
        imageMenuEN.getItems().add("Example 2");
        imageMenuEN.getItems().add("Load image");

        imageMenuPL.getItems().add("Kształty");
        imageMenuPL.getItems().add("Krzywe");
        imageMenuPL.getItems().add("Przykład Handel");
        imageMenuPL.getItems().add("Logo");
        imageMenuPL.getItems().add("Linie");
        imageMenuPL.getItems().add("Przykład 1");
        imageMenuPL.getItems().add("Przykład 2");
        imageMenuPL.getItems().add("Wczytaj obraz");

        languageMenuENAction.getItems().add("English");
        languageMenuENAction.getItems().add("Polski");
//
//        languageMenuPLAction.getItems().add("Angielski");
//        languageMenuPLAction.getItems().add("Polski");

        //TODO: domyślny język zapisany przez kogoś np w pliku pobierac i ifem ustawiać
        languageMenuPLAction.setVisible(false);
        imageMenuPL.setVisible(false);
    }

    @FXML
    void imageMenuENAction(ActionEvent event) throws IOException {
        int selectedIndex = imageMenuEN.getSelectionModel().getSelectedIndex();
        if(selectedIndex == 0) {
            chooseTestImage(1);
        } else if(selectedIndex == 1) {
            chooseTestImage(2);
        } else if(selectedIndex == 2) {
            chooseTestImage(3);
        }else if(selectedIndex == 3) {
            chooseTestImage(4);
        }else if(selectedIndex == 4) {
            chooseTestImage(5);
        }else if(selectedIndex == 5) {
            chooseTestImage(6);
        }else if(selectedIndex == 6) {
            chooseTestImage(7);
        }
        else if(selectedIndex == 7) {
            choose();
        }
    }

    @FXML
    void imageMenuPLAction(ActionEvent event) throws IOException {
        int selectedIndex = imageMenuPL.getSelectionModel().getSelectedIndex();
        if(selectedIndex == 0) {
            chooseTestImage(1);
        } else if(selectedIndex == 1) {
            chooseTestImage(2);
        } else if(selectedIndex == 2) {
            chooseTestImage(3);
        }else if(selectedIndex == 3) {
            chooseTestImage(4);
        }else if(selectedIndex == 4) {
            chooseTestImage(5);
        }else if(selectedIndex == 5) {
            chooseTestImage(6);
        }else if(selectedIndex == 6) {
            chooseTestImage(7);
        }
        else if(selectedIndex == 7) {
            choose();
        }
    }


    @FXML
    void languageMenuAction(ActionEvent event) throws IOException {
        String languageName = languageMenuENAction.getSelectionModel().getSelectedItem();
        System.out.println("menuEn"+languageName);
        if(languageName.equals("English"))
        {
            exitLabel.setText("Exit");
            titleLabel.setText("Two pass connected-component labeling");
            nextLabel.setText("Next iteration");
            titleLabel1.setText("Input image");
            titleLabel2.setText("Stage 1");
            titleLabel3.setText("Stage 2");
            firstStage.setText("First stage");
            secondStage.setText("Second stage");

            imageMenuEN.setVisible(true);
            imageMenuPL.setVisible(false);
//            languageMenuPLAction.setVisible(false);
//            languageMenuENAction.setVisible(true);
        }
        else if(languageName.equals("Polski"))
        {
            exitLabel.setText("Wyjdź");
            titleLabel.setText("Indeksacja Dwuprzebiegowa");
            nextLabel.setText("Następna iteracja");
            titleLabel1.setText("Obraz wejściowy");
            titleLabel2.setText("Etap 1");
            titleLabel3.setText("Etap 2");
            firstStage.setText("Pierwszy etap");
            secondStage.setText("Drugi etap");
            imageMenuEN.setVisible(false);
            imageMenuPL.setVisible(true);

//            languageMenuPLAction.setVisible(true);
//            imageMenuPL.setVisible(true);
//            languageMenuENAction.setVisible(false);
//            imageMenuEN.setVisible(false);
        }
    }

//    @FXML
//    void languageMenuPLAction(ActionEvent event) throws IOException {
//        String languageName = languageMenuENAction.getSelectionModel().getSelectedItem();
//        System.out.println("menuEn"+languageName);
//        if(languageName.equals("Angielski"))
//        {
//            System.out.println("menuPl w equals.Angielski"+languageName);
//            System.out.println("Ang menu pl");
//            exitLabel.setText("Exit");
//            titleLabel.setText("Two pass connected-component labeling");
//            nextLabel.setText("Next iteration");
//            titleLabel1.setText("Input image");
//            titleLabel2.setText("Stage 1");
//            titleLabel3.setText("Stage 2");
//
//            languageMenuPLAction.setVisible(false);
//            imageMenuPL.setVisible(false);
//            languageMenuENAction.setVisible(true);
//            imageMenuEN.setVisible(true);
//        }
//        else if(languageName.equals("Polski"))
//        {
//            System.out.println("menuPl w equals.Polski"+languageName);
//            System.out.println("Pl menupl");
//            exitLabel.setText("Wyjdź");
//            titleLabel.setText("Indeksacja Dwuprzebiegowa");
//            nextLabel.setText("Następna iteracja");
//            titleLabel1.setText("Obraz wejściowy");
//            titleLabel2.setText("Etap 1");
//            titleLabel3.setText("Etap 2");
//
//            languageMenuPLAction.setVisible(true);
//            imageMenuPL.setVisible(true);
//            languageMenuENAction.setVisible(false);
//            imageMenuEN.setVisible(false);
//        }
//    }

    @FXML
    void chooseTestImage(int imageNumber) {
        imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        switch (imageNumber)
        {
            case 1:
                image = new Image("file:src/main/resources/assets/figure_1.png");
                imageView1.setImage(image);
                break;
            case 2:
                image = new Image("file:src/main/resources/assets/figure_2.png");
                imageView1.setImage(image);
                break;
            case 3:
                image = new Image("file:src/main/resources/assets/figure_3.png");
                imageView1.setImage(image);
                break;
            case 4:
                image = new Image("file:src/main/resources/assets/figure_4.png");
                imageView1.setImage(image);
                break;
            case 5:
                image = new Image("file:src/main/resources/assets/figure_5.png");
                imageView1.setImage(image);
                break;
            case 6:
                image = new Image("file:src/main/resources/assets/figure_6.png");
                imageView1.setImage(image);
                break;
            case 7:
                image = new Image("file:src/main/resources/assets/figure_7.png");
                imageView1.setImage(image);
                break;
            default:
                System.out.println("Error");
                break;
        }

        table1.setVisible(false);
        table1.getColumns().clear();
        table2.setVisible(false);
        table2.getColumns().clear();
        numerOfLabels=0;
        stage = 0;
    }

    @FXML
    void choose() {
        imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagePath = file.getPath();
            image = new Image(imagePath);
            imageView1.setImage(image);
        }

        table1.setVisible(false);
        table1.getColumns().clear();
        table2.setVisible(false);
        table2.getColumns().clear();
        numerOfLabels=0;
        stage = 0;
    }

    @FXML
    void next(ActionEvent event) {
        System.out.println("Etap: " + (stage+1));
        if (stage != -1) {
            if (stage == 0) {
                width = (int) image.getWidth();
                height = (int) image.getHeight();

                binary = new int[width][height];
                labels = new int[width][height];
                tablicaSklejenRozmiar = 255;
                tablicaSklejen = new int[2][tablicaSklejenRozmiar];
                tablicaSklejen2 = new int[2][tablicaSklejenRozmiar];
                colour = new String[31];
                colour[0] = "0xFF0000ff";
                colour[1] = "0x00FF00ff";
                colour[2] = "0x0000FFff";
                colour[3] = "0xAA0000ff";
                colour[4] = "0x0AAAA0ff";
                colour[5] = "0xAA00AAff";
                colour[6] = "0xBBFF00ff";
                colour[7] = "0x00BB00ff";
                colour[8] = "0x00AABBff";
                colour[9] = "0xABCDEFff";

                colour[10] = "0xCC6699ff";
                colour[11] = "0xCC9966ff";
                colour[12] = "0x66ccb3ff";
                colour[13] = "0x55ccb3ff";
                colour[14] = "0x667fccff";
                colour[15] = "0x6666ccff";
                colour[16] = "0x7f66ccff";
                colour[17] = "0x9966ccff";
                colour[18] = "0xb366ccff";
                colour[19] = "0xcc66ccff";
                colour[20] = "0xcc6699ff";

                colour[21] = "0xcc6666ff";
                colour[22] = "0x3366ffff";
                colour[23] = "0x426ef0ff";
                colour[24] = "0xcc33ffff";
                colour[25] = "0xff3366ff";
                colour[26] = "0x804000ff";
                colour[27] = "0xfa9938ff";
                colour[28] = "0x331a00ff";
                colour[29] = "0x33ffffff";
                colour[30] = "0x33ff99ff";

                L = 0;

                for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                    tablicaSklejen[0][i] = i + 1;
                    tablicaSklejen2[0][i] = i + 1;
                }

                reader = image.getPixelReader();
                dest = new WritableImage(width, height);
                writer = dest.getPixelWriter();

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        labels[x][y] = 0;
                        if (reader.getColor(x, y).toString().equals("0x000000ff")) {
                            Color c = new Color(0, 0, 0, reader.getColor(x, y).getOpacity());
                            writer.setColor(x, y, c);
                            binary[x][y] = 1;
                        } else if (reader.getColor(x, y).toString().equals("0xffffffff")) {
                            Color c = new Color(1, 1, 1, reader.getColor(x, y).getOpacity());
                            writer.setColor(x, y, c);
                            binary[x][y] = 0;
                        }
                    }
                }

                int[][] binaryReverse = new int[height][width];
                int[][] labelsReverse = new int[height][width];

                for(int x = 0; x < height; x++)
                {
                    for(int y = 0; y < width; y++)
                    {
                        binaryReverse[x][y] = binary[y][x];
                        labelsReverse[x][y] = labels[y][x];
                    }
                }

                binary = new int[height][width];
                labels = new int[height][width];


                for(int x = 0; x < height; x++)
                {
                    for(int y = 0; y < width; y++)
                    {
                        binary[x][y] = binaryReverse[x][y];
                        labels[x][y] = labelsReverse[x][y];
                    }
                }

                List<String> done = new ArrayList<String>();;

                for (int x = 1; x < height-1; x++) {
                    for (int y = 1; y < width-1; y++) {
                        if (binary[x][y] == 1) {
                            A = labels[x - 1][y - 1];
                            B = labels[x-1][y];
                            C = labels[x - 1][y + 1];
                            D = labels[x][y-1];

                            if (A == 0 && B == 0 && C == 0 && D == 0) // Nowa etykieta
                            {
                                L += 1;
                                labels[x][y] = L;

                                //Modyfikacja tablicy sklejeń start
                                for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                    if (tablicaSklejen[1][i] == 0) {
                                        numerOfLabels++;
                                        tablicaSklejen[1][i] = labels[x][y];
                                        tablicaSklejen2[1][i] = labels[x][y];
                                        break;
                                    }
                                }
                                //Modyfikacja tablicy sklejeń koniec

                            } else {

                                // Jedna etykieta w pobliżu start

                                if (A != 0 && B == 0 && C == 0 && D == 0) {
                                    labels[x][y] = A;
                                }

                                if (A == 0 && B != 0 && C == 0 && D == 0) {
                                    labels[x][y] = B;
                                }

                                if (A == 0 && B == 0 && C != 0 && D == 0) {
                                    labels[x][y] = C;
                                }

                                if (A == 0 && B == 0 && C == 0 && D != 0) {
                                    labels[x][y] = D;
                                }

                                // Dwie etykiety w pobliżu start

                                if (A != 0 && B != 0 && C == 0 && D == 0) {
                                    if (A <= B) {
                                        labels[x][y] = A;

                                        if(!done.contains(B + "" + A)) {
                                            done.add(B + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    } else {
                                        labels[x][y] = B;

                                        if(!done.contains(A + "" + B)) {
                                            done.add(A + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A != 0 && B == 0 && C != 0 && D == 0) {
                                    if (A <= C) {
                                        labels[x][y] = A;

                                        if(!done.contains(C + "" + A)) {
                                            done.add(C + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }


                                    } else {
                                        labels[x][y] = C;

                                        if (!done.contains(A + "" + C)) {
                                            done.add(A + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A != 0 && B == 0 && C == 0 && D != 0) {
                                    if (A <= D) {
                                        labels[x][y] = A;

                                        if(!done.contains(D + "" + A)) {
                                            done.add(D + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                    } else {
                                        labels[x][y] = D;

                                        if(!done.contains(A + "" + D)) {
                                            done.add(A + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A == 0 && B != 0 && C != 0 && D == 0) {
                                    if (B <= C) {
                                        labels[x][y] = B;

                                        if(!done.contains(C + "" + B)) {
                                            done.add(C + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                    } else {
                                        labels[x][y] = C;

                                        if(!done.contains(B + "" + C)) {
                                            done.add(B + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A == 0 && B != 0 && C == 0 && D != 0) {
                                    if (B <= D) {
                                        labels[x][y] = B;

                                        if(!done.contains(D + "" + B)) {
                                            done.add(D + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                    } else {
                                        labels[x][y] = D;

                                        if(!done.contains(B + "" + D)) {
                                            done.add(B + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A == 0 && B == 0 && C != 0 && D != 0) {
                                    if (C < D) {
                                        labels[x][y] = C;

                                        if(!done.contains(D + "" + C)) {
                                            done.add(D + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                    } else {
                                        labels[x][y] = D;

                                        if(!done.contains(C + "" + D)) {
                                            done.add(C + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                // Trzy etykiety w pobliżu start

                                if (A != 0 && B != 0 && C != 0 && D == 0) {
                                    if (A <= B && A <= C) {
                                        labels[x][y] = A;

                                        if(!done.contains(B + "" + A)) {
                                            done.add(B + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(C + "" + A)) {
                                            done.add(C + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (B <= A && B <= C) {
                                        labels[x][y] = B;

                                        if(!done.contains(A + "" + B)) {
                                            done.add(A + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(C + "" + B)) {
                                            done.add(C + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (C <= A && C <= B) {
                                        labels[x][y] = C;

                                        if(!done.contains(A + "" + C)) {
                                            done.add(A + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(B + "" + C)) {
                                            done.add(B + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A != 0 && B != 0 && C == 0 && D != 0) {
                                    if (A <= B && A <= D) {
                                        labels[x][y] = A;

                                        if(!done.contains(B + "" + A)) {
                                            done.add(B + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + A)) {
                                            done.add(D + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }


                                    }

                                    if (B <= A && B <= D) {
                                        labels[x][y] = B;

                                        if(!done.contains(A + "" + B)) {
                                            done.add(A + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + B)) {
                                            done.add(D + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (D <= A && D <= B) {
                                        labels[x][y] = D;

                                        if(!done.contains(B + "" + D)) {
                                            done.add(B + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(A + "" + D)) {
                                            done.add(A + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A != 0 && B == 0 && C != 0 && D != 0) {
                                    if (A <= C && A <= D) {
                                        labels[x][y] = A;

                                        if(!done.contains(C + "" + A)) {
                                            done.add(C + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + A)) {
                                            done.add(D + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (C <= A && C <= D) {
                                        labels[x][y] = C;

                                        if(!done.contains(A + "" + C)) {
                                            done.add(A + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + C)) {
                                            done.add(D + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                    }

                                    if (D <= A && D <= C) {
                                        labels[x][y] = D;

                                        if(!done.contains(A + "" + D)) {
                                            done.add(A + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(C + "" + D)) {
                                            done.add(C + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                if (A == 0 && B != 0 && C != 0 && D != 0) {
                                    if (B <= C && B <= D) {
                                        labels[x][y] = B;

                                        if(!done.contains(C + "" + B)) {
                                            done.add(C + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + B)) {
                                            done.add(D + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (C <= B && C <= D) {
                                        labels[x][y] = C;

                                        if(!done.contains(B + "" + C)) {
                                            done.add(B + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + C)) {
                                            done.add(D + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (D <= B && D <= C) {
                                        labels[x][y] = D;

                                        if(!done.contains(B + "" + D)) {
                                            done.add(B + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(C + "" + D)) {
                                            done.add(C + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }

                                // Cztery etykiety w pobliżu start

                                if (A != 0 && B != 0 && C != 0 && D != 0) {

                                    if (A <= B && A <= C && A <= D) {
                                        labels[x][y] = A;

                                        if(!done.contains(B + "" + A)) {
                                            done.add(B + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                        if(!done.contains(C + "" + A)) {
                                            done.add(C + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + A)) {
                                            done.add(D + "" + A);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = A;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (B <= A && B <= C && B <= D) {
                                        labels[x][y] = B;

                                        if(!done.contains(A + "" + B)) {
                                            done.add(A + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(C + "" + B)) {
                                            done.add(C + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + B)) {
                                            done.add(D + "" + B);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = B;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }

                                    if (C <= A && C <= B && C <= D) {
                                        labels[x][y] = C;

                                        if(!done.contains(A + "" + C)) {
                                            done.add(A + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(B + "" + C)) {
                                            done.add(B + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(D + "" + C)) {
                                            done.add(D + "" + C);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == D) {
                                                    tablicaSklejen[1][i] = C;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }


                                    }
                                    if (D <= A && D <= B && D <= C) {
                                        labels[x][y] = D;

                                        if(!done.contains(A + "" + D)) {
                                            done.add(A + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == A) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(B + "" + D)) {
                                            done.add(B + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == B) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }

                                        if(!done.contains(C + "" + D)) {
                                            done.add(C + "" + D);
                                            //Modyfikacja tablicy sklejeń start
                                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                                if (tablicaSklejen[1][i] == C) {
                                                    tablicaSklejen[1][i] = D;
                                                    break;
                                                }
                                            }
                                            //Modyfikacja tablicy sklejeń koniec
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                binaryReverse = new int[width][height];
                labelsReverse = new int[width][height];

                for(int x = 0; x < width; x++)
                {
                    for(int y = 0; y < height; y++)
                    {
                        binaryReverse[x][y] = binary[y][x];
                        labelsReverse[x][y] = labels[y][x];
                    }
                }

                binary = new int[width][height];
                labels = new int[width][height];


                for(int x = 0; x < width; x++)
                {
                    for(int y = 0; y < height; y++)
                    {
                        binary[x][y] = binaryReverse[x][y];
                        labels[x][y] = labelsReverse[x][y];
                    }
                }

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (binary[x][y] == 1) {
                            if (labels[x][y] == 1) {
                                Color c = Color.web(colour[0]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 2) {
                                Color c = Color.web(colour[1]);
                                writer.setColor(x, y, c);

                            }
                            if (labels[x][y] == 3) {
                                Color c = Color.web(colour[2]);
                                writer.setColor(x, y, c);

                            }
                            if (labels[x][y] == 4) {
                                Color c = Color.web(colour[3]);
                                writer.setColor(x, y, c);

                            }
                            if (labels[x][y] == 5) {
                                Color c = Color.web(colour[4]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 6) {
                                Color c = Color.web(colour[5]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 7) {
                                Color c = Color.web(colour[6]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 8) {
                                Color c = Color.web(colour[7]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 9) {
                                Color c = Color.web(colour[8]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 10) {
                                Color c = Color.web(colour[9]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 11) {
                                Color c = Color.web(colour[10]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 12) {
                                Color c = Color.web(colour[11]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 13) {
                                Color c = Color.web(colour[12]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 14) {
                                Color c = Color.web(colour[13]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 15) {
                                Color c = Color.web(colour[14]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 16) {
                                Color c = Color.web(colour[15]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 17) {
                                Color c = Color.web(colour[16]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 18) {
                                Color c = Color.web(colour[17]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 19) {
                                Color c = Color.web(colour[18]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 20) {
                                Color c = Color.web(colour[19]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 21) {
                                Color c = Color.web(colour[20]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 22) {
                                Color c = Color.web(colour[21]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 23) {
                                Color c = Color.web(colour[22]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 24) {
                                Color c = Color.web(colour[23]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 25) {
                                Color c = Color.web(colour[24]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 26) {
                                Color c = Color.web(colour[25]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 27) {
                                Color c = Color.web(colour[26]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 28) {
                                Color c = Color.web(colour[27]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 29) {
                                Color c = Color.web(colour[28]);
                                writer.setColor(x, y, c);
                            }
                            if (labels[x][y] == 30) {
                                Color c = Color.web(colour[29]);
                                writer.setColor(x, y, c);
                            }
                        } else if(binary[x][y] == 0)
                        {
                            Color c = Color.web("0xffffffff");
                            writer.setColor(x, y, c);
                        }
                    }
                }

                setTable(table1, tablicaSklejen);

                System.out.println(Arrays.toString(tablicaSklejen[0]));
                System.out.println(Arrays.toString(tablicaSklejen[1]));

                imageView2.setImage(dest);
                stage++;
            } else if (stage == 1) {

                //Sklejenie obiektów start
                for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                    for (int j = i; j < tablicaSklejenRozmiar ; j++) {
                        if(tablicaSklejen[0][i] == tablicaSklejen[1][j])
                        {
                            tablicaSklejen[1][j] = tablicaSklejen[1][i];
                        }
                    }
                }
                //Sklejenie obiektów koniec

                //Uporządkowanie tablicy sklejeń start
                List<Integer> labelSort = new ArrayList<>();

                for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                    if(!labelSort.contains(tablicaSklejen[1][i]) && tablicaSklejen[1][i] != 0)
                    {
                        labelSort.add(tablicaSklejen[1][i]);
                    }
                }

                int ilosc = labelSort.size();

                for(int i = 0; i < ilosc; i++) {
                    for (int j = 0; j < tablicaSklejenRozmiar; j++)
                    {
                        if(tablicaSklejen[1][j]==labelSort.get(i))
                        {
                            tablicaSklejen[1][j]=i+1;
                        }
                    }
                }

                System.out.println(Arrays.toString(tablicaSklejen[0]));
                System.out.println(Arrays.toString(tablicaSklejen[1]));
                //Uporządkowanie tablicy sklejeń koniec

                //Podmienienie etykiet start
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (binary[x][y] == 1) {
                            for (int i = 0; i < tablicaSklejenRozmiar; i++) {
                                if(tablicaSklejen2[1][i] == labels[x][y]){
                                    labels[x][y] = tablicaSklejen[1][i];
                                }
                            }
                        }
                    }
                }
                //Podmienienie etykiet koniec

                setTable(table2, tablicaSklejen);

                reader2 = image.getPixelReader();
                dest2 = new WritableImage(width, height);
                writer2 = dest2.getPixelWriter();

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (binary[x][y] == 1) {
                            if (labels[x][y] == 1) {
                                Color c = Color.web(colour[0]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 2) {
                                Color c = Color.web(colour[1]);
                                writer2.setColor(x, y, c);

                            }
                            if (labels[x][y] == 3) {
                                Color c = Color.web(colour[2]);
                                writer2.setColor(x, y, c);

                            }
                            if (labels[x][y] == 4) {
                                Color c = Color.web(colour[3]);
                                writer2.setColor(x, y, c);

                            }
                            if (labels[x][y] == 5) {
                                Color c = Color.web(colour[4]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 6) {
                                Color c = Color.web(colour[5]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 7) {
                                Color c = Color.web(colour[6]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 8) {
                                Color c = Color.web(colour[7]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 9) {
                                Color c = Color.web(colour[8]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 10) {
                                Color c = Color.web(colour[9]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 11) {
                                Color c = Color.web(colour[10]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 12) {
                                Color c = Color.web(colour[11]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 13) {
                                Color c = Color.web(colour[12]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 14) {
                                Color c = Color.web(colour[13]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 15) {
                                Color c = Color.web(colour[14]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 16) {
                                Color c = Color.web(colour[15]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 17) {
                                Color c = Color.web(colour[18]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 18) {
                                Color c = Color.web(colour[17]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 19) {
                                Color c = Color.web(colour[18]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 20) {
                                Color c = Color.web(colour[19]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 21) {
                                Color c = Color.web(colour[20]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 22) {
                                Color c = Color.web(colour[21]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 23) {
                                Color c = Color.web(colour[22]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 24) {
                                Color c = Color.web(colour[23]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 25) {
                                Color c = Color.web(colour[24]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 26) {
                                Color c = Color.web(colour[25]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 27) {
                                Color c = Color.web(colour[26]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 28) {
                                Color c = Color.web(colour[27]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 29) {
                                Color c = Color.web(colour[28]);
                                writer2.setColor(x, y, c);
                            }
                            if (labels[x][y] == 30) {
                                Color c = Color.web(colour[29]);
                                writer2.setColor(x, y, c);
                            }
                        } else if(binary[x][y] == 0)
                        {
                            Color c = Color.web("0xffffffff");
                            writer2.setColor(x, y, c);
                        }
                    }
                }
                imageView3.setImage(dest2);
                stage = -1;

            }
        }
    }

    private void  setTable(TableView<String[]> table, int[][] dataArray) {
        table.setVisible(true);
        firstStage.setVisible(true);
        String[][] staffArray = new String[dataArray.length][];

        for(int i = 0; i < dataArray.length; i++){
            staffArray[i] = new String[numerOfLabels];
            for(int j=0; j<numerOfLabels; j++){
                staffArray[i][j] = Integer.toString(dataArray[i][j]);
            }
        }
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data
        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            table.getColumns().add(tc);
        }
        table.setItems(data);
    }

    @FXML
    void exitGame(ActionEvent event) {
        Platform.exit();
    }


    private Color[] generateColors(int n)
    {
        String[] colour = new String[6];
        colour[0] = "A";
        colour[1] = "B";
        colour[2] = "C";
        colour[3] = "D";
        colour[4] = "E";
        colour[5] = "F";

        Color[] cols = new Color[n];
        for(int i = 0; i < n; i++)
        {

        }
        return cols;
    }
}
