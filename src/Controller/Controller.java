package Controller;

import Model.*;
import View.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    @FXML private Button loadButton;


    private List<Point> plist;

    Board boardObj;
    Rule rule;
    Timeline timeline;
    GraphicsContext gc;
    Model.RLEParser rleParser;
    public static Controller instance;
    Stage stage;
    public int counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;

        //noe

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
        scaleSlider.setMax(40);

        scaleSlider.valueProperty().addListener((o, oldValue, newValue) -> {
            boardObj.setCellSize(newValue.intValue());
            boardObj.drawBoardWithGrid();
        });
    }

    public void colorStage() {

        try {
            Pane root = FXMLLoader.load(getClass().getResource("colorStage.fxml"));
            stage = new Stage();
            Scene scene = new Scene(root, 270, 112);
            stage.setScene(scene);
            stage.setTitle("Change color");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println("feil");
            e.printStackTrace();
        }

        ColorStageController.instance.setCellColorPicker(boardObj.getcellColor());
        ColorStageController.instance.setBackgroundColorPicker(boardObj.getBackgroundColor());


    }

    public void draw() {
        boardObj = new Board(canvas);
        boardObj.drawBoardWithGrid();
    }

    public void nextGeneration() {

        rule = new Rule(boardObj.board);
        boardObj.setBoard(rule.conwaysBoardRules());
        boardObj.drawBoardWithGrid();
        counter += 1;
        genCounter();
    }

    public void genCounter() {
        int gen = counter;
        Main.getStage().setTitle("Conways Game of Life (" + gen + ")");
    }

    public void reset() {
        boardObj = new Board(canvas);
        boardObj.drawBoardWithGrid();
    }

    public void startStopButtonAction() {

        if ((timeline == null) || (timeline.getStatus() != Animation.Status.RUNNING)) {

            timeline = new Timeline(new KeyFrame(Duration.millis(70), ae -> nextGeneration() ));
            timeline.setCycleCount(Animation.INDEFINITE);


            timeline.play();
            startStopButton.setText("Stop");

        } else if ( (timeline != null) && (timeline.getStatus() == Animation.Status.RUNNING) ) {

            timeline.stop();
            startStopButton.setText("Start");
        }

    }

    public void loadFile() throws IOException {
        rleParser = new RLEParser();
        boardObj.setBoard(rleParser.importFile());
        nextGeneration();
        counter = 0;
    }

    public void exportFile() {
        SaveFile er = new SaveFile();
        er.testRun();
        printArray(boardObj.getBoard());
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

    public void changeCellColor(Color c) {
        boardObj.setCellColor(c);
    }

    public void changeBackgroundColor(Color c) {
        boardObj.setBackgroundColor(c);
    }


    public void printArray(byte[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println("");
        }
    }
}
