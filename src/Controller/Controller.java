package Controller;

import Model.Board;
import Model.Rule;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public Canvas canvas;
    @FXML private Button nextGenButton;
    @FXML private Button previousGenButton;
    @FXML private Button stopButton;
    @FXML private Button startButton;
    @FXML private Button clearButton;
    @FXML private Button resetButton;
    @FXML private GridPane gridPane;
    @FXML private Slider scaleSlider;
    @FXML private Slider speedSlider;
    @FXML private ColorPicker cellColorPicker;
    @FXML private ColorPicker backgroundColorPicker;

    Board boardObj;
    Rule rule;
    Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draw();
        setID();
    }

    public void setID() {
        nextGenButton.setId("nextGenerationButton");
        previousGenButton.setId("previousGenerationButton");
        startButton.setId("startButton");
        stopButton.setId("stopButton");
        clearButton.setId("clearButton");
        resetButton.setId("resetButton");
        gridPane.setId("gridPane");
        scaleSlider.setId("scaleSlider");
        speedSlider.setId("speedSlider");
        cellColorPicker.setId("cellColorPicker");
        backgroundColorPicker.setId("backgroundColorPicker");
    }

    // hjelpemetode som tegner grafikk til 'canvas' området i GUI
    public void draw() {
        boardObj = new Board(canvas);
        boardObj.drawBoardWithGrid();
    }

    public void nextGeneration() {

        rule = new Rule(boardObj.board);
        boardObj.setBoard(rule.conwaysBoardRules());
        boardObj.drawBoardWithGrid();

    }

    public void reset() {
        boardObj = new Board(canvas);
        boardObj.drawBoardWithGrid();
    }

    public void start() {

        if ((timeline == null) || (timeline.getStatus() != Animation.Status.RUNNING)) {

            int gen = 1000;
            double durationInMilliSeconds = 70;


            timeline = new Timeline(new KeyFrame(Duration.millis(durationInMilliSeconds), ae -> nextGeneration() ));

            timeline.setCycleCount(gen);
            timeline.play();

        }

    }

    public void stop() {

        if ( (timeline != null) && (timeline.getStatus() == Animation.Status.RUNNING) ) {
            timeline.stop();
            timeline = null;
        }

    }


    public void clear() {
        boardObj.clearBoard();
    }


}
