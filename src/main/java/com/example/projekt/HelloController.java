package com.example.projekt;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.util.Callback;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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

    @FXML
    private Button exitLabel;

    @FXML
    private Button nextLabel;

    @FXML
    private ComboBox<String> languageMenuPLAction;

    @FXML
    private ComboBox<String> languageMenuENAction;

    @FXML
    private Label titleLabel;

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
    private Button grafButton;

    @FXML
    private Button tablicaSklejenButton;

    @FXML
    private Button directoryButton;

    @FXML
    private Button fileButton;

    @FXML
    private Label methodSelected;

    @FXML
    private Label methodTitle;

    @FXML
    private Label fileOptionsTitle;

    @FXML
    private Label methodsTitle;

    private Image image;
    private int stage;
    private int width;
    private int height;
    private int[][] binary;
    private int[][] labels;
    private int tablicaSklejenRozmiar;
    private int[][] tablicaSklejen;
    private int[][] tablicaSklejen2;
    private List<String> colour;
    private int A, B, C, D;
    private int L;
    private int numerOfLabels;
    private PixelReader reader;
    private WritableImage dest;
    private WritableImage dest2;
    private PixelWriter writer;
    private PixelWriter writer2;
    private int method;
    private String defaultDirectory;
    private String languageName;
    private int directorySet;
    private int grafRozmiar;
    private int[][] graf;
    private int[][] graf2;

    @FXML
    void initialize(){

        languageMenuENAction.getItems().add("English");
        languageMenuENAction.getItems().add("Polski");

        languageMenuPLAction.getItems().add("Angielski");
        languageMenuPLAction.getItems().add("Polski");

        languageName = "English";

        //TODO: domyślny język zapisany przez kogoś np w pliku pobierac i ifem ustawiać
        languageMenuPLAction.setVisible(false);
        method = 0;
        stage = -1;
        directorySet = 0;

        firstStage.setVisible(false);
        firstStage.setText("Graph");
        secondStage.setVisible(false);
    }

    @FXML
    void chooseDirectory(ActionEvent event) throws ZipException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if(selectedDirectory == null){
            //No Directory selected
        }else{
            defaultDirectory = selectedDirectory.getAbsolutePath();
            directorySet = 1;

            new ZipFile("src/main/resources/test_images.zip").extractAll(defaultDirectory);
        }
    }

    public static void unzip(){
        String source = "some/compressed/file.zip";
        String destination = "some/destination/folder";
        String password = "password";

        try {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void chooseFile(ActionEvent event) {
        //imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        FileChooser chooser = new FileChooser();
        if(directorySet == 1)
        {
            String intialDirectory = Paths.get(defaultDirectory).toAbsolutePath().normalize().toString();
            chooser.setInitialDirectory(new File(intialDirectory));
        }
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
        firstStage.setVisible(false);
        secondStage.setVisible(false);
        tablicaSklejenButton.setDisable(false);
        grafButton.setDisable(false);
    }

    @FXML
    void languageMenuAction(ActionEvent event) throws IOException {
        languageName = languageMenuENAction.getSelectionModel().getSelectedItem();
        if(languageName.equals("English"))
        {
            exitLabel.setText("Exit");
            titleLabel.setText("Two pass connected-component labeling");
            nextLabel.setText("Next iteration");
            titleLabel1.setText("Input image");
            titleLabel2.setText("Stage 1");
            titleLabel3.setText("Stage 2");
            if(method == 0){
                firstStage.setText("Graph");
            } else {
                firstStage.setText("First stage");
            }
            secondStage.setText("Second stage");
            directoryButton.setText("Choose default directory");
            fileButton.setText("Load file");
            methodTitle.setText("Selected method");
            fileOptionsTitle.setText("File management");
            methodsTitle.setText("Methods");
            tablicaSklejenButton.setText("Equivalence table");
            grafButton.setText("Graph");
            if(method == 0) {
                methodSelected.setText("Graph");
            } else {
                methodSelected.setText("Equivalence table");
            }
        }
        else if(languageName.equals("Polski"))
        {
            exitLabel.setText("Wyjdź");
            titleLabel.setText("Indeksacja Dwuprzebiegowa");
            nextLabel.setText("Następna iteracja");
            titleLabel1.setText("Obraz wejściowy");
            titleLabel2.setText("Etap 1");
            titleLabel3.setText("Etap 2");
            if(method == 0){
                firstStage.setText("Graf");
            } else {
                firstStage.setText("Pierwszy etap");
            }
            secondStage.setText("Drugi etap");
            directoryButton.setText("Wybierz folder domyślny");
            fileButton.setText("Wybierz plik");
            methodTitle.setText("Wybrana metoda");
            fileOptionsTitle.setText("Zarządzanie plikami");
            methodsTitle.setText("Metody");
            tablicaSklejenButton.setText("Tablica sklejeń");
            grafButton.setText("Graf");
            if(method == 0) {
                methodSelected.setText("Graf");
            } else {
                methodSelected.setText("Tablica sklejeń");
            }
        }
    }

    @FXML
    void tablicaSklejen(ActionEvent event) {
        if(method != 1) {
            method = 1;

            if(languageName.equals("English"))
            {
                methodSelected.setText("Equivalence table");
                firstStage.setText("First stage");
            }
            else if(languageName.equals("Polski"))
            {
                firstStage.setText("Pierwszy etap");
                methodSelected.setText("Tablica sklejeń");
            }

            if(stage != 0) {
                //imageView1.setImage(null);
                imageView2.setImage(null);
                imageView3.setImage(null);
                table1.setVisible(false);
                table1.getColumns().clear();
                table2.setVisible(false);
                table2.getColumns().clear();
                firstStage.setVisible(false);
                secondStage.setVisible(false);
                numerOfLabels = 0;
                stage = 0;
            }
        }
    }

    @FXML
    void graf(ActionEvent event) {
        if(method != 0) {
            method = 0;

            if(languageName.equals("English"))
            {
                methodSelected.setText("Graph");
                firstStage.setText("Graph");
            }
            else if(languageName.equals("Polski"))
            {
                methodSelected.setText("Graf");
                firstStage.setText("Graf");
            }

            if(stage != 0) {
                //imageView1.setImage(null);
                imageView2.setImage(null);
                imageView3.setImage(null);
                table1.setVisible(false);
                table1.getColumns().clear();
                table2.setVisible(false);
                table2.getColumns().clear();
                numerOfLabels = 0;
                firstStage.setVisible(false);
                secondStage.setVisible(false);
                stage = 0;
            }
        }
    }

    @FXML
    void next(ActionEvent event) {
        if (stage != -1) {
            if (stage == 0) {

                grafButton.setDisable(true);
                tablicaSklejenButton.setDisable(true);

                if(method == 0)
                {
                    grafEtapPierwszy();
                    firstStage.setVisible(true);
                }
                else {
                    firstStage.setVisible(true);
                    tablicaSklejenEtapPierwszy();
                }
                stage++;
            } else if (stage == 1) {
                if(method == 0)
                {
                    grafEtapDrugi();
                }
                else {
                    secondStage.setVisible(true);
                    tablicaSklejenEtapDrugi();
                }

                grafButton.setDisable(false);
                tablicaSklejenButton.setDisable(false);

                stage = -1;
            }
        }
    }

    private void  setTable(TableView<String[]> table, int[][] dataArray) {
        table.setVisible(true);
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

    void generateColours(){
        colour = new ArrayList<String>();

        colour.add("0xFF0000ff");
        colour.add("0x00FF00ff");
        colour.add("0x0000FFff");
        colour.add("0xAA0000ff");
        colour.add("0x0AAAA0ff");
        colour.add("0xAA00AAff");
        colour.add("0xBBFF00ff");
        colour.add("0x00BB00ff");
        colour.add("0x00AABBff");
        colour.add("0xABCDEFff");
        colour.add("0xCC6699ff");
        colour.add("0xCC9966ff");
        colour.add("0x66ccb3ff");
        colour.add("0x55ccb3ff");
        colour.add("0x667fccff");
        colour.add("0x6666ccff");
        colour.add("0x7f66ccff");
        colour.add("0x9966ccff");
        colour.add("0xb366ccff");
        colour.add("0xcc66ccff");
        colour.add("0xcc6699ff");
        colour.add("0xcc6666ff");
        colour.add("0x3366ffff");
        colour.add("0x426ef0ff");
        colour.add("0xcc33ffff");
        colour.add("0xff3366ff");
        colour.add("0x804000ff");
        colour.add("0xfa9938ff");
        colour.add("0x331a00ff");
        colour.add("0x33ffffff");
        colour.add("0x33ff99ff");


        if(numerOfLabels > 31) {
            for (int i = 31; i < numerOfLabels; i++) {
                Random obj = new Random();
                int rand_num = obj.nextInt(0xffffff + 1);
                String colorCode = String.format("#%06x", rand_num);

                if (!colour.contains(colorCode)) {
                    colour.add(colorCode);
                }
            }
        }
    }

    void tablicaSklejenEtapPierwszy(){
        width = (int) image.getWidth();
        height = (int) image.getHeight();

        binary = new int[width][height];
        labels = new int[width][height];
        tablicaSklejenRozmiar = 1024;
        tablicaSklejen = new int[2][tablicaSklejenRozmiar];
        tablicaSklejen2 = new int[2][tablicaSklejenRozmiar];

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
                    if(x == 0 || y == 0 || x == width-1 || y == height-1)
                    {
                        Color c = new Color(1, 1, 1, reader.getColor(x, y).getOpacity());
                        writer.setColor(x, y, c);
                        binary[x][y] = 0;
                    }
                    else
                    {
                        Color c = new Color(0, 0, 0, reader.getColor(x, y).getOpacity());
                        writer.setColor(x, y, c);
                        binary[x][y] = 1;
                    }
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
                            if (C <= D) {
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

        generateColours();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c;
                if (binary[x][y] == 1) {
                    c = Color.web(colour.get(labels[x][y] - 1));
                }
                else
                {
                    c = Color.web("0xffffffff");
                }
                writer.setColor(x, y, c);

            }
        }

        setTable(table1, tablicaSklejen);

        imageView2.setImage(dest);

    }

    void tablicaSklejenEtapDrugi(){

        //Sklejenie obiektów start
        for (int i = 0; i < tablicaSklejenRozmiar; i++) {
            for (int j = 0; j < tablicaSklejenRozmiar ; j++) {
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

        dest2 = new WritableImage(width, height);
        writer2 = dest2.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c;
                if (binary[x][y] == 1) {
                    c = Color.web(colour.get(labels[x][y] - 1));
                }
                else
                {
                    c = Color.web("0xffffffff");
                }
                writer2.setColor(x, y, c);

            }
        }
        imageView3.setImage(dest2);

    }

    //------------------------------------------------------------------------------------------------------------------

    int root(int Arr[ ],int i)
    {
        while(Arr[i-1] != i)
        {
            i = Arr[i-1];
        }
        return i;
    }

    void union(int p ,int q)
    {
        if(p != q)
        {
            if(p > q)
            {
                int x = q;
                q = p;
                p = x;
            }
            int root_p = root(graf[1], p);
            int root_q = root(graf[1], q);
            graf[1][root_p-1] = root_q ;

        }
    }

    void grafEtapPierwszy(){
        width = (int) image.getWidth();
        height = (int) image.getHeight();

        binary = new int[width][height];
        labels = new int[width][height];

        grafRozmiar = 1024;
        graf = new int[2][grafRozmiar];
        graf2 = new int[2][grafRozmiar];

        L = 0;

        for (int i = 0; i < grafRozmiar; i++) {
            graf[0][i] = i + 1;
            graf2[0][i] = i + 1;
        }

        reader = image.getPixelReader();
        dest = new WritableImage(width, height);
        writer = dest.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                labels[x][y] = 0;
                if (reader.getColor(x, y).toString().equals("0x000000ff")) {
                    if(x == 0 || y == 0 || x == width-1 || y == height-1)
                    {
                        Color c = new Color(1, 1, 1, reader.getColor(x, y).getOpacity());
                        writer.setColor(x, y, c);
                        binary[x][y] = 0;
                    }
                    else
                    {
                        Color c = new Color(0, 0, 0, reader.getColor(x, y).getOpacity());
                        writer.setColor(x, y, c);
                        binary[x][y] = 1;
                    }
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

                        //Modyfikacja grafu start
                        for (int i = 0; i < grafRozmiar; i++) {
                            if (graf[1][i] == 0) {
                                numerOfLabels++;
                                graf[1][i] = labels[x][y];
                                graf2[1][i] = labels[x][y];
                                break;
                            }
                        }
                        //Modyfikacja grafu koniec

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

                                if (!done.contains(B + "" + A)) {
                                    done.add(B + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, B);
                                    //Modyfikacja grafu koniec
                                }

                            } else {
                                labels[x][y] = B;

                                if(!done.contains(A + "" + B)) {
                                    done.add(A + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, A);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        if (A != 0 && B == 0 && C != 0 && D == 0) {
                            if (A <= C) {
                                labels[x][y] = A;

                                if (!done.contains(C + "" + A)) {
                                    done.add(C + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, C);
                                    //Modyfikacja grafu koniec
                                }


                            } else {
                                labels[x][y] = C;

                                if (!done.contains(A + "" + C)) {
                                    done.add(A + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, A);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        if (A != 0 && B == 0 && C == 0 && D != 0) {
                            if (A <= D) {
                                labels[x][y] = A;

                                if (!done.contains(D + "" + A)) {
                                    done.add(D + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, D);
                                    //Modyfikacja grafu koniec
                                }

                            } else {
                                labels[x][y] = D;

                                if(!done.contains(A + "" + D)) {
                                    done.add(A + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, A);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        if (A == 0 && B != 0 && C != 0 && D == 0) {
                            if (B <= C) {
                                labels[x][y] = B;

                                if (!done.contains(C + "" + B)) {
                                    done.add(C + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, C);
                                    //Modyfikacja grafu koniec
                                }

                            } else {
                                labels[x][y] = C;

                                if(!done.contains(B + "" + C)) {
                                    done.add(B + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, B);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        if (A == 0 && B != 0 && C == 0 && D != 0) {
                            if (B <= D) {
                                labels[x][y] = B;

                                if (!done.contains(D + "" + B)) {
                                    done.add(D + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, D);
                                    //Modyfikacja grafu koniec
                                }

                            } else {
                                labels[x][y] = D;

                                if(!done.contains(B + "" + D)) {
                                    done.add(B + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, B);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        if (A == 0 && B == 0 && C != 0 && D != 0) {
                            if (C <= D) {
                                labels[x][y] = C;

                                if (!done.contains(D + "" + C)) {
                                    done.add(D + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, D);
                                    //Modyfikacja grafu koniec
                                }
                            } else {
                                labels[x][y] = D;

                                if(!done.contains(C + "" + D)) {
                                    done.add(C + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, C);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        // Trzy etykiety w pobliżu start

                        if (A != 0 && B != 0 && C != 0 && D == 0) {
                            if (A <= B && A <= C) {
                                labels[x][y] = A;

                                if (!done.contains(B + "" + A)) {
                                    done.add(B + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, B);
                                    //Modyfikacja grafu koniec
                                }

                                if (!done.contains(C + "" + A)) {
                                    done.add(C + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, C);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (B <= A && B <= C) {
                                labels[x][y] = B;

                                if (!done.contains(A + "" + B)) {
                                    done.add(A + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, A);
                                    //Modyfikacja grafu koniec
                                }

                                if (!done.contains(C + "" + B)) {
                                    done.add(C + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, C);
                                    //Modyfikacja grafu koniec
                                }

                            }

                            if (C <= A && C <= B) {
                                labels[x][y] = C;

                                if (!done.contains(A + "" + C)) {
                                    done.add(A + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, A);
                                    //Modyfikacja grafu koniec
                                }

                                if (!done.contains(B + "" + C)) {
                                    done.add(B + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, B);
                                    //Modyfikacja grafu koniec
                                }

                            }
                        }

                        if (A != 0 && B != 0 && C == 0 && D != 0) {
                            if (A <= B && A <= D) {
                                labels[x][y] = A;

                                if (!done.contains(B + "" + A)) {
                                    done.add(B + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, B);
                                    //Modyfikacja grafu koniec
                                }


                                if (!done.contains(D + "" + A)) {
                                    done.add(D + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, D);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (B <= A && B <= D) {
                                labels[x][y] = B;

                                if (!done.contains(A + "" + B)) {
                                    done.add(A + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, A);
                                    //Modyfikacja grafu koniec
                                }

                                if (!done.contains(D + "" + B)) {
                                    done.add(D + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, A);
                                    //Modyfikacja grafu koniec
                                }

                            }

                            if (D <= A && D <= B) {
                                labels[x][y] = D;

                                 if (!done.contains(B + "" + D)) {
                                     done.add(B + "" + D);
                                     //Modyfikacja grafu start
                                     union(D, A);
                                     //Modyfikacja grafu koniec
                                 }


                                if (!done.contains(A + "" + D)) {
                                    done.add(A + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, B);
                                    //Modyfikacja grafu koniec
                                }

                            }
                        }

                        if (A != 0 && B == 0 && C != 0 && D != 0) {
                            if (A <= C && A <= D) {
                                labels[x][y] = A;

                                if (!done.contains(C + "" + A)) {
                                    done.add(C + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, C);
                                    //Modyfikacja grafu koniec
                                }

                                if (!done.contains(D + "" + A)) {
                                    done.add(D + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, D);
                                    //Modyfikacja grafu koniec
                                }

                            }

                            if (C <= A && C <= D) {
                                labels[x][y] = C;

                                if (!done.contains(A + "" + C)) {
                                    done.add(A + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, A);
                                    //Modyfikacja grafu koniec
                                }

                                if (!done.contains(D + "" + C)) {
                                    done.add(D + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, D);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (D <= A && D <= C) {
                                labels[x][y] = D;

                                if(!done.contains(A + "" + D)) {
                                    done.add(A + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, A);
                                    //Modyfikacja grafu koniec
                                }


                                if(!done.contains(C + "" + D)) {
                                    done.add(C + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, C);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        if (A == 0 && B != 0 && C != 0 && D != 0) {
                            if (B <= C && B <= D) {
                                labels[x][y] = B;

                                if(!done.contains(C + "" + B)) {
                                    done.add(C + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, C);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(D + "" + B)) {
                                    done.add(D + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, D);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (C <= B && C <= D) {
                                labels[x][y] = C;

                                if(!done.contains(B + "" + C)) {
                                    done.add(B + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, B);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(D + "" + C)) {
                                    done.add(D + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, D);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (D <= B && D <= C) {
                                labels[x][y] = D;

                                if(!done.contains(B + "" + D)) {
                                    done.add(B + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, B);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(C + "" + D)) {
                                    done.add(C + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, C);
                                    //Modyfikacja grafu koniec
                                }
                            }
                        }

                        // Cztery etykiety w pobliżu start

                        if (A != 0 && B != 0 && C != 0 && D != 0) {

                            if (A <= B && A <= C && A <= D) {
                                labels[x][y] = A;

                                if(!done.contains(B + "" + A)) {
                                    done.add(B + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, B);
                                    //Modyfikacja grafu koniec
                                }
                                if(!done.contains(C + "" + A)) {
                                    done.add(C + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, C);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(D + "" + A)) {
                                    done.add(D + "" + A);
                                    //Modyfikacja grafu start
                                    union(A, D);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (B <= A && B <= C && B <= D) {
                                labels[x][y] = B;

                                if(!done.contains(A + "" + B)) {
                                    done.add(A + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, A);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(C + "" + B)) {
                                    done.add(C + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, C);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(D + "" + B)) {
                                    done.add(D + "" + B);
                                    //Modyfikacja grafu start
                                    union(B, D);
                                    //Modyfikacja grafu koniec
                                }
                            }

                            if (C <= A && C <= B && C <= D) {
                                labels[x][y] = C;

                                if(!done.contains(A + "" + C)) {
                                    done.add(A + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, A);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(B + "" + C)) {
                                    done.add(B + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, B);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(D + "" + C)) {
                                    done.add(D + "" + C);
                                    //Modyfikacja grafu start
                                    union(C, D);
                                    //Modyfikacja grafu koniec
                                }


                            }
                            if (D <= A && D <= B && D <= C) {
                                labels[x][y] = D;

                                if(!done.contains(A + "" + D)) {
                                    done.add(A + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, A);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(B + "" + D)) {
                                    done.add(B + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, B);
                                    //Modyfikacja grafu koniec
                                }

                                if(!done.contains(C + "" + D)) {
                                    done.add(C + "" + D);
                                    //Modyfikacja grafu start
                                    union(D, C);
                                    //Modyfikacja grafu koniec
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

        //System.out.println(Arrays.toString(graf[1]));

        generateColours();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c;
                if (binary[x][y] == 1) {
                    c = Color.web(colour.get(labels[x][y]-1));
                }
                else
                {
                    c = Color.web("0xffffffff");
                }
                writer.setColor(x, y, c);
            }
        }

        setTable(table1, graf);

        imageView2.setImage(dest);

    }

    void grafEtapDrugi(){

        //Sklejenie obiektów start
        for (int i = 0; i < grafRozmiar; i++) {
            for (int j = 0; j < grafRozmiar ; j++) {
                if(graf[0][i] == graf[1][j])
                {
                    graf[1][j] = graf[1][i];
                }
            }
        }
        //Sklejenie obiektów koniec

        //Uporządkowanie tablicy sklejeń start
        List<Integer> labelSort = new ArrayList<>();

        for (int i = 0; i < grafRozmiar; i++) {
            if(!labelSort.contains(graf[1][i]) && graf[1][i] != 0)
            {
                labelSort.add(graf[1][i]);
            }
        }

        int ilosc = labelSort.size();

        for(int i = 0; i < ilosc; i++) {
            for (int j = 0; j < grafRozmiar; j++)
            {
                if(graf[1][j]==labelSort.get(i))
                {
                    graf[1][j]=i+1;
                }
            }
        }
        //Uporządkowanie tablicy sklejeń koniec

        //Podmienienie etykiet start
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (binary[x][y] == 1) {
                    for (int i = 0; i < graf[1].length; i++) {
                        if(graf2[1][i] == labels[x][y]){
                            labels[x][y] = graf[1][i];
                        }
                    }
                }
            }
        }
        //Podmienienie etykiet koniec

        //setTable(table2, graf);

        dest2 = new WritableImage(width, height);
        writer2 = dest2.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c;
                if (binary[x][y] == 1) {
                    c = Color.web(colour.get(labels[x][y]-1));
                }
                else
                {
                    c = Color.web("0xffffffff");
                }
                writer2.setColor(x, y, c);
            }
        }
        imageView3.setImage(dest2);
    }
}
