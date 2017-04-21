package Controller;

import Model.*;
import View.Main;
import javafx.animation.Animation;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    ///kommentar

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
    Stage gifStage;
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

        scaleSlider.setValue(2);
        scaleSlider.setMin(2);
        scaleSlider.setMax(10);

        scaleSlider.valueProperty().addListener((o, oldValue, newValue) -> {
            boardObj.setCellSize(newValue.intValue());
            boardObj.drawBoard();
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
        boardObj.drawBoard();

        System.out.println(boardObj);
    }

    public void nextGeneration() {
        rule = new Rule(boardObj.board);
        boardObj.setBoard(rule.conwaysBoardRules());
        boardObj.drawBoard();
        counter += 1;
        genCounter();
    }

    public void genCounter() {
        int gen = counter;
        Main.getStage().setTitle("Conways Game of Life (" + gen + ")");
    }

    public void reset() {
        boardObj.setBoard(boardObj.initialBoard);
        boardObj.drawBoard();
    }

    public void loadFile() throws IOException {
        rleParser = new RLEParser();
        byte[][] temp = rleParser.importFile();
        if (temp != null) {
            Rule.setRules(rleParser.getSurvive(), rleParser.getBorn());
            boardObj.clearBoard();
            boardObj.addBoard(temp);
            //boardObj.drawBoard();
            boardObj.drawBoard();
            counter = 0;
        }
    }

    public void saveFile() {
        SaveFile sf = new SaveFile();
        sf.saveFile(boardObj.getBoard());
    }

    public void newBlankAction() {
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

    public void toggleGrid() {

        if (boardObj.getGrid()) {
            boardObj.setGrid(false);

        } else {
            boardObj.setGrid(true);

        }

        boardObj.drawBoard();
    }

    public void openExportMenu() {

        try {
            Pane root = FXMLLoader.load(getClass().getResource("gif.fxml"));
            gifStage = new Stage();
            Scene scene = new Scene(root, 290, 110);

            gifStage.setScene(scene);
            gifStage.setTitle("Export as GIF");
            gifStage.setResizable(false);
            gifStage.show();

        } catch (Exception e) {
            System.out.println("Cannot open GIF Export Menu");
            e.printStackTrace();
        }

    }


}
