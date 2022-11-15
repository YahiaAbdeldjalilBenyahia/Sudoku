/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import Config.Clear;
import Config.Notation;
import Config.Undo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class Sudoku extends Application {

    private final static int FRAMEWIDTH = 1000, FRAMEHEIGHT = 700;

    public final static int X_OFFSET = 0, Y_OFFSET = 0, SQUARE = 60;
    public static final Font normalFont = new Font("Verdana", Sudoku.SQUARE / 2);
    public static final Font triggerFont = new Font("Times New Roman", Sudoku.SQUARE / 2 + 5);
    public static final Font possibilityFont = new Font("Times New Roman", 20);
    public static final String normalStyle = "";
    public static final String blueStyle = "-fx-background-color: #89BDC2;"
            + "-fx-background-radius: 1px;";
    public static final String grayStyle = "-fx-background-color: #C2C2C2;"
            + "-fx-background-radius: 1px;";
    public static final String redStyle = "-fx-background-color: #C26F6F;"
            + "-fx-background-radius: 1px;";
    public static final int digitsSize = 5;
    public static final int digits_x_offset = 9 * SQUARE + 30;
    public static final int digits_y_offset = 100;

    public final static Group root = new Group();

    final Color backgroundColor = Color.CYAN;
    final static int DIFFICULTY = 70; // [0 , 100] 
    static List<Field> fields = new ArrayList<>();
    static Field[][] array = new Field[9][9];
    static List<Digit> digits = new ArrayList<>();

    public static Field selectedField = null;
    final Text time = new Text();
    public static Stack<Function<Object, Object>> stack = new Stack();
    public static Stack<Object> inputStack = new Stack();

    @Override
    public void start(Stage primaryStage) {
        SetupFields();
        SetupDigits();
        SetupOptions();
        SetupBorders();
        SetupEnvironement();
        Scene scene = new Scene(root, FRAMEWIDTH, FRAMEHEIGHT, backgroundColor);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    void SetupOptions() {
        Notation n = new Notation();
        Undo u = new Undo();
        Clear c = new Clear();
    }

    void SetupDigits() {
        for (byte i = 1; i <= 9; i++) {
            Digit d = new Digit(i);
            digits.add(d);
        }
    }

    void SetupBorders() {
        for (int i = 0; i <= 3; i++) {
            Line line = new Line(0, i * 3 * SQUARE, 9 * SQUARE + X_OFFSET, i * 3 * SQUARE);

            root.getChildren().add(line);
        }
        for (int i = 0; i <= 3; i++) {
            Line line = new Line(i * 3 * SQUARE, 0, i * 3 * SQUARE, Y_OFFSET + 9 * SQUARE);

            root.getChildren().add(line);
        }
    }

    void SetupFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int r = (int) (Math.random() * 100);
                Field field = new Field(i, j, r > DIFFICULTY);
                array[i][j] = field;

                fields.add(field);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (array[i][j].isInitialized()) {
                    //a number that doesnt exist in b-l-c => put it
                    array[i][j].put(getNumber(i, j));
                }
            }
        }
    }

    int getNumber(int y, int x) {
        int[] freq = new int[10];
        //fill from line
        for (int j = 0; j < 9; j++) {
            freq[array[y][j].getInfo()]++;
        }
        //fill from column
        for (int i = 0; i < 9; i++) {
            freq[array[i][x].getInfo()]++;
        }
        //fill from box
        for (int i = y / 3 * 3; i < y / 3 * 3 + 3; i++) {
            for (int j = x / 3 * 3; j < x / 3 * 3 + 3; j++) {
                freq[array[i][j].getInfo()]++;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (freq[i] == 0) {
                list.add(i);
            }
        }
        return list.isEmpty() ? 0 : list.get((int) (Math.random() * list.size()));
    }

    public static double iToPx(int i) {
        return i * SQUARE + Y_OFFSET;
    }

    public static double jToPx(int j) {
        return j * SQUARE + X_OFFSET;
    }

    public static double pxToI(double px) {
        return (px - Y_OFFSET) / SQUARE;
    }

    public static double pxToJ(double px) {
        return (px - Y_OFFSET) / SQUARE;
    }

    public static void main(String[] args) {
        launch(args);
    }

    int s = 0, m = 0, h = 0;

    private void SetupEnvironement() {
        Timer t = new Timer();
        time.setFont(new Font("Times New Roman", 60));
        time.setFill(Color.BLACK);
        time.setTranslateY(FRAMEHEIGHT - 70);
        time.setTranslateX(3.2 * SQUARE);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                s++;
                m = s / 60;
                h = m / 60;
                time.setText("0" + String.valueOf(h) + ":" + (m % 60 <= 9 ? "0" : "") + String.valueOf(m % 60) + ":" + (s % 60 <= 9 ? "0" : "") + String.valueOf(s % 60));
            }
        }, 0, 1000);
        m = s / 60;
        h = m / 60;

        root.getChildren().add(time);
    }

}
