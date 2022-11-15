/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import sudoku.Sudoku;
import sudoku.Field;
/**
 *
 * @author DELL
 */
public class Clear {

    private Button button;
    private String normalStyle = "-fx-border-color: #000000 ;-fx-border-radius: 0;";
    private String ONstyle = "-fx-background-color: #17517e;"
            + "-fx-border-color: #000000;"
            + "-fx-border-radius: 0;";

    public Clear() {
        SetupButton();
    }
    private void SetupButton() {
        button = new Button("⌫");
        button.setStyle("-fx-border-color: #000000 ;-fx-border-radius: 0;");
        button.setTranslateX(9 * Sudoku.SQUARE + 25 + 2 * Sudoku.SQUARE);
        button.setTranslateY(35);
        button.setScaleX(1.7);
        button.setScaleY(2);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!Sudoku.selectedField.isInitialized()) {
                    Sudoku.selectedField.getTextField().setText("");
                }
                for(int i = 0 ; i < 9 ;i++)
                {
                    Sudoku.selectedField.nots[i].setVisible(false);
                }
            }
        });
        Sudoku.root.getChildren().add(button);
    }
}
