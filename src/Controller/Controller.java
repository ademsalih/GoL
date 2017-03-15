package Controller;

import Model.Board;
import Model.Point;
import Model.Rule;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public Canvas canvas;
    @FXML private Button nextGenButton;
    @FXML private Button startStopButton;
    @FXML private GridPane gridPane;
    @FXML private Slider scaleSlider;
    @FXML private Slider speedSlider;


    private List<Point> plist;

    @FXML private Button loadButton;


    Board boardObj;
    Rule rule;
    Timeline timeline;
    GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plist = new ArrayList<Point>();

        draw();
        setID();
        initializeSliders();
    }

    public void setID() {
        nextGenButton.setId("nextGenerationButton");
        gridPane.setId("gridPane");
        scaleSlider.setId("scaleSlider");
        speedSlider.setId("speedSlider");
    }

    public void initializeSliders() {
        speedSlider.setMin(1);
        speedSlider.setMax(50);


        speedSlider.valueProperty().addListener((o, oldValue, newValue) -> {
            timeline.setRate(newValue.doubleValue());
        });

        scaleSlider.setValue(5);
        scaleSlider.setMin(5);
        scaleSlider.setMax(100);

        scaleSlider.valueProperty().addListener((o, oldValue, newValue) -> {
            boardObj.setCellSize(newValue.intValue());
            boardObj.drawBoardWithGrid();
        });
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



    public void startStopButtonAction() {

        if ((timeline == null) || (timeline.getStatus() != Animation.Status.RUNNING)) {

            timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> nextGeneration() ));
            timeline.setCycleCount(Animation.INDEFINITE);


            timeline.play();
            startStopButton.setText("Stop");

        } else if ( (timeline != null) && (timeline.getStatus() == Animation.Status.RUNNING) ) {

            timeline.stop();
            startStopButton.setText("Start");
        }

    }


    public void loadFile() {
        try {
            Model.RLEParser.settingX();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clear() {
        boardObj.clearBoard();
    }

    public void mouseClicked(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseclickedonBoard(p.x, p.y);



    }

    public void mouseDragged(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();


    }

    public void exitEvent() {
        System.exit(0);
    }



}
