package com.example.projekt;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
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
    void choose(ActionEvent event) {
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
                colour = new String[10];
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
                        } else if(binary[x][y] == 0)
                        {
                            Color c = Color.web("0xffffffff");
                            writer2.setColor(x, y, c);
                        }
                    }

                    imageView3.setImage(dest2);
                    stage = -1;
                }
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
