package Controller;

import Model.*;
import Model.DynamicFiles.DynamicRLEParser;
import Model.StaticFiles.StaticBoard;
import Model.StaticFiles.StaticRLEParser;
import Model.StaticFiles.StaticRule;
import Model.DynamicFiles.DynamicBoard;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Model.DynamicFiles.DynamicRule;

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

    public DynamicBoard boardObj;
    public DynamicRule rule;
    public DynamicRLEParser rleParser;

    /*
    public StaticBoard boardObj;
    public StaticRule rule;
    public StaticRLEParser rleParser;*/

    private List<Point> plist;
    public Timeline timeline;
    public GraphicsContext gc;
    public static Controller instance;
    public Stage stage;
    public Stage gifStage;

    public int counter;
    public String titleName;

    public Animate animate;
    public URLDialog url;

    public Stage urlStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;
        titleName = "Game of Life";

        plist = new ArrayList<Point>();

        draw();
        setID();
        initializeSliders();
        animate = new Animate();
        rule = new DynamicRule();

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
        boardObj = new DynamicBoard(canvas, 350, 250);
        //boardObj = new StaticBoard(canvas);
        boardObj.drawBoard();
    }

    // Uses the StaticRule class to iterate to next generation and draws the game.
    public void nextGeneration() {
        rule.setCurrentBoard(boardObj.getBoard());
        rule.calculateBoardOfActiveCells();

        double time = System.currentTimeMillis();

        List<List<Byte>> tempArr = rule.conwaysBoardRules();

        double timeTaken = System.currentTimeMillis() - time;
        System.out.println("conwaysBoardRules: " + timeTaken);

        boardObj.setBoard(tempArr);
        boardObj.drawBoard();
        
        counter++;
        Main.getStage().setTitle(titleName + " (" + counter + ")");
    }

    public void updateTitle(String newName) {
        Main.getStage().setTitle(newName);
        titleName = newName;
    }

    // Resets the game to the first state and stops the animation.
    public void reset() {
        boardObj.setBoard(boardObj.initialBoard);
        boardObj.drawBoard();
        counter = 0;
        animate.stopAnimation();
        updateTitle(titleName);
    }

    // Loads an RLE files and draws the file to the canvas.
    public void loadFileFromDisk() throws IOException {
        //rleParser = new StaticRLEParser();
        rleParser = new DynamicRLEParser();
        rleParser.importFromDisk();
        //byte[][] temp = rleParser.getBoard();
        List<List<Byte>> temp  = rleParser.getBoard();
        if (temp != null) {
            StaticRule.setRules(rleParser.getSurvive(), rleParser.getBorn());
            boardObj.clearBoard();
            boardObj.addBoard(temp);
            boardObj.drawBoard();
            counter = 0;
            updateTitle(rleParser.getPatternName());
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
        updateTitle("Game of Life");
    }

    // Draws cell on canvas when clicked.
    public void mouseClicked(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseClickedOrDraggedOnBoard(p.x, p.y);
    }

    public void mouseDragged(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseClickedOrDraggedOnBoard(p.x, p.y);
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
        url = new URLDialog();
        url.showStage();
        String urlString;
        if ((urlString = url.getURL()) != null){
            System.out.println("It's not null");
            DynamicRLEParser rle = new DynamicRLEParser();
            //StaticRLEParser rle = new StaticRLEParser();
            try {
                rle.importFromURL(urlString);
                boardObj.clearBoard();
                boardObj.addBoard(rle.getBoard());
                boardObj.drawBoard();
                counter = 0;
                updateTitle("Pattern from URL");
            } catch (IOException e) {
                FileHandling.alert("Unable to find or read from url");
            }
        }


    }


}
