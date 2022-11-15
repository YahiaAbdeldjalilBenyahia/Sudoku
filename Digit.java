/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.function.Function;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author DELL
 */
public class Digit {

    private Button button;
    private byte digit;
    private static final String normalStyle = "-fx-border-color: #000000 ;-fx-border-radius: 0;";
    private static final String selectedStyle = "-fx-background-color: #DF711B;"
            + "-fx-border-color: #000000 ;-fx-border-radius: 0;";

    public Digit() {
    }

    public Digit(byte digit) {
        this.digit = digit;
        SetupButton();
    }

    private void SetupButton() {
        button = new Button(String.valueOf(digit));
        button.setScaleX(2.4);
        button.setScaleY(2.4);
        button.setStyle("-fx-border-color: #000000 ;-fx-border-radius: 0;");
        button.setTranslateX(Sudoku.digits_x_offset + ((digit - 1) % 3) * Sudoku.SQUARE);
        button.setTranslateY(Sudoku.digits_y_offset + ((digit - 1) / 3) * Sudoku.SQUARE);
        button.setOnAction(e -> {
            for (Digit d : Sudoku.digits) {
                d.Normalise();
            }
            Field.selectedDigit = this.digit;
            System.out.println(Field.selectedDigit);
            Trigger();
        });

        Sudoku.root.getChildren().add(button);
    }

    public void Trigger() {
        button.setStyle(selectedStyle);
    }

    public void Normalise() {
        button.setStyle(normalStyle);
    }

    private boolean Check() {
        for (int i = 0; i < 9; i++) {
            if (Sudoku.array[Sudoku.selectedField.getI()][i].getInfo() == digit) {
                Sudoku.array[Sudoku.selectedField.getI()][i].animate();
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (Sudoku.array[i][Sudoku.selectedField.getJ()].getInfo() == digit) {
                Sudoku.array[i][Sudoku.selectedField.getJ()].animate();
                return false;
            }
        }
        for (int i = Sudoku.selectedField.getI() / 3 * 3; i < Sudoku.selectedField.getI() / 3 * 3 + 3; i++) {
            for (int j = Sudoku.selectedField.getJ() / 3 * 3; j < Sudoku.selectedField.getJ() / 3 * 3; j++) {
                if (Sudoku.array[i][j].getInfo() == digit) {
                    Sudoku.array[i][j].animate();
                    return false;
                }
            }
        }
        return true;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public byte getDigit() {
        return digit;
    }

    public void setDigit(byte digit) {
        this.digit = digit;
    }

}
