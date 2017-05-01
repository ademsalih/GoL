package Controller;

import Model.*;
import View.Main;
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
//import org.hibernate.annotations.SourceType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class that handles user inputs i.e. button click and slider
 * in the main Stage of the application.
 */

public class Controller implements Initializable {

    // Connection between FXML objects and this controller class.
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
    public StaticBoard boardObj;
    public StaticRule rule;
    //public DynamicBoard boardObj;
    //public DynamicRule rule;
    //public Board boardObj;
    //public Rule rule;
    public Timeline timeline;
    public GraphicsContext gc;
    public RLEParser_Static rleParser;
    public static Controller instance;
    public Stage stage;
    public Stage gifStage;
    public int counter;
    public Animate animate;

    public Stage urlStage;

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

    // Sets the ID of the objects in this class for CSS styling.
    public void setID() {
        nextGenButton.setId("nextGenButton");
        gridPane.setId("gridPane");
        scaleSlider.setId("scaleSlider");
        speedSlider.setId("speedSlider");
        startStopButton.setId("startButton");
        menuBar.setId("menuBar");
        resetButton.setId("resetButton");
    }

    // Instatiats the slider with minimum, maximum and start values.
    public void initializeSliders() {
        speedSlider.setMin(1);
        speedSlider.setMax(30);

        speedSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            animate.setAnimationRate(newValue.intValue());
        });

        scaleSlider.setValue(2);
        scaleSlider.setMin(2);
        scaleSlider.setMax(10);

        scaleSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            boardObj.setCellSize(newValue.intValue());
            boardObj.drawBoard();
        });
    }

    // Creates the "Change color" Stage and sends the board colors to the Stage.
    public void colorStage() {

        try {
            Pane root = FXMLLoader.load(getClass().getResource("/View/FXML/colorStage.fxml"));
            stage = new Stage();
            Scene scene = new Scene(root, 270, 112);

            scene.getStylesheets().add("/View/CSS/colorStageSS.css");
            stage.setScene(scene);
            stage.setTitle("Change color");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println("feil");
            e.printStackTrace();
        }


        // Disse "sender" fargene på brettet over til fargevelger! Må justeres for dynamic board
        //ColorStageController.instance.setCellColorPicker(boardObj.getcellColor());
        //ColorStageController.instance.setBackgroundColorPicker(boardObj.getBackgroundColor());

    }

    // Method that uses the draw method of StaticBoard class for drawing the game to canvas.
    public void draw() {
        //boardObj = new DynamicBoard(canvas, 350, 250);
        boardObj = new StaticBoard(canvas);
        boardObj.drawBoard();
    }

    // Uses the StaticRule class to iterate to next generation and draws the game.
    public void nextGeneration() {
        rule = new StaticRule(boardObj.board);
        //rule = new DynamicRule(boardObj.board);
        boardObj.setBoard(rule.conwaysBoardRules());
        boardObj.drawBoard();
        counter += 1;
        genCounter();
    }

    // Counts the generations of the game and displays on top of Stage.
    public void genCounter() {
        int gen = counter;
        Main.getStage().setTitle("Conways Game of Life (" + gen + ")");
    }

    // Resets the game to the first state and stops the animation.
    public void reset() {
        //boardObj.setBoard(boardObj.initialBoard);
        boardObj.drawBoard();
        counter = 0;
        animate.stopAnimation();
    }

    // Loads an RLE files and draws the file to the canvas.
    public void loadFileFromDisk() throws IOException {
        rleParser = new RLEParser_Static();
        rleParser.importFromDisk();
        //List<List<Byte>> temp = rleParser.importAsList();
        byte[][] temp = rleParser.getBoard();
        if (temp != null) {
            StaticRule.setRules(rleParser.getSurvive(), rleParser.getBorn());
            boardObj.clearBoard();
            boardObj.addBoard(temp);
            boardObj.drawBoard();
            counter = 0;
            Main.getStage().setTitle(rleParser.getPatternName());

        }
    }

    // Saves the current game as a RLE.file.
    public void saveFile() {
        SaveFile sf = new SaveFile();
        //sf.saveFile(boardObj.getBoard());
    }

    // Creates a new black board.
    public void newBlankAction() {
        boardObj.clearBoard();
        counter = 0;
        Main.getStage().setTitle("Conways Game of Life");
    }

    // Draws cell on canvas when clicked.
    public void mouseClicked(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseclickedordraggedonBoard(p.x, p.y);
    }

    public void mouseDragged(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseclickedordraggedonBoard(p.x, p.y);
    }

    // Exits the application.
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

    // Toggles the "Start/Stop" button.
    public void startStopButton() {
        animate.startStopButtonAction();
    }

    // Toggles the grid using boolean value in StaticBoard class.
    public void toggleGrid() {
        /*
        if (boardObj.getGrid()) {
            boardObj.setGrid(false);

        } else {
            boardObj.setGrid(true);

        }

        boardObj.drawBoard();
        */
    }

    // Creates the "GIF Export" Stage and shows the Stage.
    public void openExportMenu() {

        try {
            Pane root = FXMLLoader.load(getClass().getResource("/View/FXML/gifMenu.fxml"));
            gifStage = new Stage();
            Scene scene = new Scene(root, 517, 283  );

            gifStage.setScene(scene);
            gifStage.setTitle("Export as GIF");
            gifStage.setResizable(false);
            gifStage.show();

        } catch (Exception e) {
            System.out.println("Cannot open GIF Export Menu");
            e.printStackTrace();
        }

    }

    // Creates the "URL Import" Stage and shows the Stage.
    public void openURLMenu() {
        URLDialog url = new URLDialog();
        url.showStage();
        String urlString;
        if ((urlString = url.getURL()) != null){
            System.out.println("It's not null");
            RLEParser_Static rle = new RLEParser_Static();
            try {
                rle.importFromURL(urlString);
                boardObj.clearBoard();
                boardObj.addBoard(rle.getBoard());
                boardObj.drawBoard();
                counter = 0;
            } catch (IOException e) {
                FileHandling.alert("Unable to find or read from url");
            }
        }


    }


}
