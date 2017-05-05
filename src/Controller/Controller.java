package Controller;

import Model.*;
import Model.Abstract.Rule;
import Model.DynamicFiles.DynamicParseToRLE;
import Model.DynamicFiles.DynamicRLEParser;
import Model.DynamicFiles.DynamicBoard;
import View.Main;
import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import Model.DynamicFiles.DynamicRule;
import javafx.util.Duration;

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

    // Check menu items
    @FXML private CheckMenuItem conwaysLife;
    @FXML private CheckMenuItem seeds;
    @FXML private CheckMenuItem flock;
    @FXML private CheckMenuItem twoByTwo;
    @FXML private CheckMenuItem maze;
    @FXML private CheckMenuItem move;
    @FXML private CheckMenuItem highLife;
    @FXML private CheckMenuItem mazectric;
    @FXML private CheckMenuItem fredkin;
    @FXML private CheckMenuItem replicator;
    @FXML private CheckMenuItem dayAndNight;
    @FXML private CheckMenuItem lifeWithoutDeath;

    public DynamicBoard boardObj;
    DynamicRule rule;
    DynamicRLEParser rleParser;

    /*
    public StaticBoard boardObj;
    public StaticRule rule;
    public StaticRLEParser rleParser;*/

    //public Timeline timeline;
    //public GraphicsContext gc;
    public static Controller instance;
    Stage stage;
    Stage gifStage;

    private int counter;
    private String titleName;

    private Animate animate;
    private URLDialog url;
    private SaveRLEDialog save;

    //public Stage urlStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;
        titleName = "Game of Life";

        List<Point> plist = new ArrayList<Point>();

        draw();
        setID();
        initializeSliders();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), actionEvent -> nextGeneration());
        animate = new Animate(keyFrame);
        rule = new DynamicRule();

        animate.setSpeed(10);


    }

    // Sets the ID of the objects in this class for CSS styling.
    private void setID() {
        nextGenButton.setId("nextGenButton");
        gridPane.setId("gridPane");
        scaleSlider.setId("scaleSlider");
        speedSlider.setId("speedSlider");
        startStopButton.setId("startButton");
        menuBar.setId("menuBar");
        resetButton.setId("resetButton");
    }

    // Instatiats the slider with minimum, maximum and start values.
    private void initializeSliders() {
        speedSlider.setMin(1);
        speedSlider.setMax(30);

        speedSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> animate.setAnimationRate(newValue.intValue()));

        scaleSlider.setValue(2);
        scaleSlider.setMin(0.3);
        scaleSlider.setMax(10);

        scaleSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            boardObj.setCellSize(newValue.doubleValue());
            System.out.println(newValue.doubleValue());
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

        ColorStageController.instance.setCellColorPicker(boardObj.getcellColor());
        ColorStageController.instance.setBackgroundColorPicker(boardObj.getBackgroundColor());
    }

    // Method that uses the draw method of StaticBoard class for drawing the game to canvas.
    private void draw() {
        boardObj = new DynamicBoard(canvas, 350, 250);
        //boardObj = new StaticBoard(canvas);
        boardObj.drawBoard();
    }

    // Uses the StaticRule class to iterate to next generation and draws the game.
    public void nextGeneration() {

        double time = System.currentTimeMillis();

        rule.setCurrentBoard(boardObj.getBoard());

        rule.calculateBoardOfActiveCells();

        List<List<Byte>> tempArr = rule.applyBoardRules();

        boardObj.setBoard(tempArr);


        boardObj.drawBoard();

        double timeTaken = System.currentTimeMillis() - time;
        System.out.println("applyBoardRules: " + timeTaken);

        counter++;
        Main.getStage().setTitle(titleName + " (" + counter + ")");


    }

    private void updateTitle(String newName) {
        Main.getStage().setTitle(newName);
        titleName = newName;
    }

    // Resets the game to the first state and stops the animation.
    public void reset() {
        boardObj.setBoard(boardObj.initialBoard);
        boardObj.drawBoard();
        counter = 0;
        stopAnimationIfRunning();
        updateTitle(titleName);
    }

    private void stopAnimationIfRunning() {
        animate.stopAnimation();
        startStopButton.setText("Start");
        startStopButton.setId("startButton");
    }

    // Loads an RLE files and draws the file to the canvas.
    public void loadFileFromDisk() throws IOException {
        stopAnimationIfRunning();
        rleParser = new DynamicRLEParser();
        rleParser.importFromDisk();
        List<List<Byte>> temp  = rleParser.getBoard();
        if (temp != null) {
            DynamicRule.setRules(rleParser.getSurvive(), rleParser.getBorn());
            boardObj.clearBoard();
            boardObj.addBoard(temp);
            boardObj.drawBoard();
            counter = 0;
            updateTitle(rleParser.getPatternName());
        }
    }


    // Creates a new black board.
    public void newBlankAction() {
        boardObj.clearBoard();
        boardObj.clearInitialBoard();
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


    void changeCellColor(Color c) {
        boardObj.setCellColor(c);
    }

    void changeBackgroundColor(Color c) {
        boardObj.setBackgroundColor(c);
    }

    public Button getStartStopButton() {
        return this.startStopButton;
    }

    // Toggles the "Start/Stop" button.
    public void startStopButton() {
        animate.startStopButtonAction();
        if (!animate.getAnimationStatus()) {
            startStopButton.setText("Stop");
            startStopButton.setId("stopButton");


        } else if (animate.getAnimationStatus()) {
            startStopButton.setText("Start");
            startStopButton.setId("startButton");
        }
    }


    // Toggles the grid using boolean value in StaticBoard class.
    public void toggleGrid() {

        if (boardObj.getGrid()) {
            boardObj.setGrid(false);
        } else {
            boardObj.setGrid(true);
        }

        boardObj.drawBoard();
    }

    // Creates the "GIF Export" Stage and shows the Stage.
    public void openExportMenu() {
        stopAnimationIfRunning();
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/View/FXML/gifMenu.fxml"));
            gifStage = new Stage();
            Scene scene = new Scene(root, 517, 283  );

            gifStage.setScene(scene);
            gifStage.setTitle("Export as GIF");
            gifStage.setResizable(false);
            gifStage.show();

            gifStage.setOnCloseRequest( ae -> {
                GIFController.instance.cancelButtonAction();
            });

        } catch (Exception e) {
            System.out.println("Cannot open GIF Export Menu");
            e.printStackTrace();
        }

    }

    /**
     * Creates the "URL Import" Stage and shows the Stage.
     */
    public void openURLMenu() {
        stopAnimationIfRunning();
        url = new URLDialog();
        url.showStage();
        String urlString;
        if ((urlString = url.getURL()) != null){
            DynamicRLEParser rle = new DynamicRLEParser();
            try {
                rle.importFromURL(urlString);
                boardObj.clearBoard();
                boardObj.addBoard(rle.getBoard());
                boardObj.drawBoard();
                counter = 0;
                updateTitle(rle.getPatternName());
            } catch (IOException e) {
                PopUps.alert("Unable to find or read from url");
            }
        }
    }

    /**
     * Saves the current board as a .rle file
     */
    public void saveRLE() {
        stopAnimationIfRunning();
        save = new SaveRLEDialog();
        save.showStage();

        // Checks if the stage was closed
        if (!save.isClosed()) {
            String name = save.getName();
            String author = save.getAuthor();
            String comment = save.getComment();

            // Setting up the object that parses the pattern to RLE
            DynamicParseToRLE drp = new DynamicParseToRLE(name, author, comment);
            // Feeds in the rules
            drp.setup(rule.getBorn(), rule.getSurvivor(), boardObj.getBoard());
            String rleTxt = drp.extractingRLE();

            SaveFile sf = new SaveFile();
            sf.saveFile(rleTxt);
            PopUps.info("Save successful");
        }
    }

    /**
     * Opens up an about information dioalog window
     */
    public void about() {
        PopUps.info("Game of Life", "A game made by: \nNarmatha Siva\nAdem Salih\nPatrik Hansen\n05.05.2017");
    }




    // Methods that applys the different rules and deals with the checking of the Rule menu
    private void unCheckAll() {
        ArrayList<CheckMenuItem> menuItems = new ArrayList<>(12);
        menuItems.addAll(Arrays.asList(conwaysLife, seeds, flock, twoByTwo, maze, move, highLife,
                mazectric, fredkin, replicator, dayAndNight, lifeWithoutDeath));

        int i = 0;
        while (i < 12) {
            menuItems.get(i).setSelected(false);
            i++;
        }
    }

    public void selectConwaysLife() {
        unCheckAll();
        conwaysLife.setSelected(true);

        int[] b = {3};
        int[] s = {2,3};

        Rule.setRules(s,b);
    }

    public void selectSeeds() {
        unCheckAll();
        seeds.setSelected(true);

        int[] b = {2};
        int[] s = {};

        Rule.setRules(s,b);
    }

    public void selectFlock() {
        unCheckAll();
        flock.setSelected(true);

        int[] b = {3};
        int[] s = {1,2};

        Rule.setRules(s,b);
    }

    public void selectTwoBytwo() {
        unCheckAll();
        twoByTwo.setSelected(true);

        int[] b = {3,6};
        int[] s = {1,2,5};

        Rule.setRules(s,b);
    }

    public void selectMaze() {
        unCheckAll();
        maze.setSelected(true);

        int[] b = {3};
        int[] s = {1,2,3,4,5};

        Rule.setRules(s,b);
    }

    public void selectMove() {
        unCheckAll();
        move.setSelected(true);

        int[] b = {3,6,8};
        int[] s = {2,4,5};

        Rule.setRules(s,b);
    }

    public void selectHighLife() {
        unCheckAll();
        highLife.setSelected(true);

        int[] b = {3,6};
        int[] s = {2,3};

        Rule.setRules(s,b);
    }

    public void selectMazctric() {
        unCheckAll();
        mazectric.setSelected(true);

        int[] b = {3};
        int[] s = {1,2,3,4};

        Rule.setRules(s,b);
    }

    public void selectFredkin() {
        unCheckAll();
        fredkin.setSelected(true);

        int[] b = {1,3,5,7};
        int[] s = {0,2,4,6,8};

        Rule.setRules(s,b);
    }

    public void selectReplicator() {
        unCheckAll();
        replicator.setSelected(true);

        int[] b = {1,3,5,7};
        int[] s = {1,3,5,7};

        Rule.setRules(s,b);
    }

    public void selectDayAndNight() {
        unCheckAll();
        dayAndNight.setSelected(true);

        int[] b = {3,6,7,8};
        int[] s = {3,4,6,7,8};

        Rule.setRules(s,b);
    }

    public void selectLifeWithoutDeath() {
        unCheckAll();
        lifeWithoutDeath.setSelected(true);

        int[] b = {3};
        int[] s = {0,1,2,3,4,5,6,7,8};

        Rule.setRules(s,b);
    }

}

