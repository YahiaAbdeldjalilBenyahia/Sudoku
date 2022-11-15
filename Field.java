/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Field {

    private TextField textField;
    private boolean initialized;
    private int i, j;
    public static boolean makePossibility = false;
    public static short selectedDigit;
    private static final Font notationsFont = new Font("Times New Roman", 15);
    public Text[] nots = new Text[9];

    public Field() {
    }

    public Field(int i, int j, boolean initialized) {
        textField = new TextField();
        this.initialized = initialized;
        this.i = i;
        this.j = j;
        initializeField();

    }

    String Unnotate(Object[] array) {
        //array[0] = textField
        //array[1] = integer
        Field tf = (Field) array[0];
        tf.nots[(Integer) array[1]].setVisible(false);

        return "";
    }

    String RemoveDigitFrom(Object f) {
        ((Field) f).getTextField().setText("");
        return "";
    }

    private void initializeField() {
        textField.setEditable(/*!initialized*/false);
        textField.setTranslateX(Sudoku.jToPx(j));
        textField.setTranslateY(Sudoku.iToPx(i));
        textField.setMaxHeight(Sudoku.SQUARE);
        textField.setMaxWidth(Sudoku.SQUARE);
        textField.setPrefSize(Sudoku.SQUARE, Sudoku.SQUARE);
        textField.setFont(Sudoku.normalFont);
        textField.setAlignment(Pos.CENTER);
        if (initialized) {
            textField.setStyle(Sudoku.grayStyle);
        }
        textField.setOnMouseClicked(e -> {
            Sudoku.selectedField = this;
            if (!isInitialized()) {
                if (Field.selectedDigit == 0) {
                    JOptionPane.showMessageDialog(null, "Please pick a digit", "Please pick a digit", i);
                } else if (Check()) {

                    textField.setText(String.valueOf(Field.selectedDigit));
                }
            }
        });
        Sudoku.root.getChildren().add(textField);
        if (!initialized) {
            for (int it = 0; it < nots.length; it++) {
                nots[it] = new Text(String.valueOf(it + 1));
                nots[it].setFont(notationsFont);
                nots[it].setStroke(Color.BLACK);
                nots[it].setTranslateX(Sudoku.jToPx(j) + ((it) % 3) * 15 + 10);
                nots[it].setTranslateY(Sudoku.iToPx(i) + ((it) / 3) * 15 + 15);
                nots[it].setVisible(false);
                Sudoku.root.getChildren().add(nots[it]);
            }
        }
    }

    private boolean Check() {
        for (int i = 0; i < 9; i++) {
            if (Sudoku.array[Sudoku.selectedField.getI()][i].getInfo() == Field.selectedDigit) {
                Sudoku.array[Sudoku.selectedField.getI()][i].animate();
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (Sudoku.array[i][Sudoku.selectedField.getJ()].getInfo() == Field.selectedDigit) {
                Sudoku.array[i][Sudoku.selectedField.getJ()].animate();
                return false;
            }
        }
        for (int i = Sudoku.selectedField.getI() / 3 * 3; i < Sudoku.selectedField.getI() / 3 * 3 + 3; i++) {
            for (int j = Sudoku.selectedField.getJ() / 3 * 3; j < Sudoku.selectedField.getJ() / 3 * 3 + 3; j++) {
                if (Sudoku.array[i][j].getInfo() == Field.selectedDigit) {
                    Sudoku.array[i][j].animate();
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOkay() {
        int[] freq = new int[10];
        for (int it = 0; it < 9; it++) {
            freq[Sudoku.array[this.i][it].getInfo()]++;
        }
        for (int it = 0; it < 9; it++) {
            freq[Sudoku.array[it][this.j].getInfo()]++;
        }

        for (int it = this.i / 3 * 3; it < this.i / 3 * 3 + 3; it++) {
            for (int jt = this.j / 3 * 3; jt < this.j / 3 * 3 + 3; jt++) {
                freq[Sudoku.array[it][jt].getInfo()]++;
            }
        }
        return freq[getInfo()] == 3;
    }

    public void Normalise() {
        if (!initialized) {
            textField.setStyle(Sudoku.normalStyle);
        } else {
            textField.setStyle(Sudoku.grayStyle);
        }
        textField.setFont(Sudoku.normalFont);
    }

    public void Trigger() {
        if (this.isOkay()) {
            textField.setStyle(Sudoku.blueStyle);
        } else {
            textField.setStyle(Sudoku.redStyle);
        }
        textField.setFont(Sudoku.triggerFont);
    }

    public int getInfo() {
        return textField.getText().equals("") ? 0 : Integer.parseInt(textField.getText().trim());
    }

    public void put(int v) {
        textField.setText(String.valueOf(v));
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public TextField getTextField() {
        return textField;
    }
    private Timeline timeLine = new Timeline();

    public void animate() {
        String os = textField.getStyle();
        timeLine.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(textField.styleProperty(), Sudoku.redStyle)
                )
        );
        timeLine.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(textField.styleProperty(), os)
                )
        );
        timeLine.play();
    }
}
