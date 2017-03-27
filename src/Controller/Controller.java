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
import javafx.scene.control.*;
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
    @FXML private MenuBar menuBar;
    @FXML private Button resetButton;
    @FXML private Label speedLabel;
    @FXML private Label scaleLabel;

    private List<Point> plist;
    Board boardObj;
    Rule rule;
    Timeline timeline;
    GraphicsContext gc;
    Model.RLEParser rleParser;
    public static Controller instance;
    Stage stage;
    public int counter;
    Animate animate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;

        plist = new ArrayList<Point>();

        draw();
        setID();
        initializeSliders();
        animate = new Animate();

        animate.setSpeed(10);
    }

    public void setID() {
        nextGenButton.setId("nextGenButton");
        gridPane.setId("gridPane");
        scaleSlider.setId("scaleSlider");
        speedSlider.setId("speedSlider");
        startStopButton.setId("startButton");
        menuBar.setId("menuBar");
        resetButton.setId("resetButton");
    }

    public void initializeSliders() {
        speedSlider.setMin(1);
        speedSlider.setMax(30);

        speedSlider.valueProperty().addListener((o, oldValue, newValue) -> {
            animate.setAnimationRate(newValue.intValue());
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

            scene.getStylesheets().add("View/colorStageSS.css");
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

    public void loadFile() throws IOException {
        rleParser = new RLEParser();
        Board.board = rleParser.importFile();
        //boardObj.setBoard();
        boardObj.drawBoardWithGrid();
        counter = 0;
    }

    public void saveFile() {
        SaveFile sf = new SaveFile();
        sf.saveFile(boardObj.getBoard());
    }

    public void clear() {
        boardObj.clearBoard();
        counter = 0;
        Main.getStage().setTitle("Conways Game of Life");
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

    public Button getStartStopButton() {
        return this.startStopButton;
    }

    public Timeline getTimeline() {
        return this.timeline;
    }


    public void startStopButton() {

        animate.startStopButtonAction();
    }


}
