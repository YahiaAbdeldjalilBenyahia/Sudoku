/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import sudoku.Sudoku;

/**
 *
 * @author DELL
 */
public class Undo {

    private Button button;
    private String normalStyle = "-fx-border-color: #000000 ;-fx-border-radius: 0;";
    private String ONstyle = "-fx-background-color: #17517e;"
            + "-fx-border-color: #000000;"
            + "-fx-border-radius: 0;";

    public Undo() {
        SetupButton();
    }

    private void SetupButton() {
        button = new Button("⎌");
        button.setStyle("-fx-border-color: #000000 ;-fx-border-radius: 0;");
        button.setTranslateX(9 * Sudoku.SQUARE + 29.5 + Sudoku.SQUARE);
        button.setTranslateY(35);
        button.setScaleX(2.2);
        button.setScaleY(2);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Execute();
            }
        });
        Sudoku.root.getChildren().add(button);
    }

    private void Execute() {
        if (!Sudoku.stack.isEmpty()) {
            Sudoku.stack.pop().apply(Sudoku.inputStack.pop());
        }
    }
}
