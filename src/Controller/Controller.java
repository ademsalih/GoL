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

    /**
     * FXML GUI elements.
     */
    @FXML public Canvas canvas;
    @FXML private Button nextGenButton;
    @FXML private Button startStopButton;
    @FXML private GridPane gridPane;
    @FXML private Slider scaleSlider;
    @FXML private Slider speedSlider;
    @FXML private MenuBar menuBar;
    @FXML private Button resetButton;

    /**
     * CheckMenuItems from FXML.
     */
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

    /**
     * Declarations of the objects needed for the program.
     */
    Stage stage;
    Stage gifStage;
    DynamicRule rule;
    private int counter;
    private URLDialog url;
    private String titleName;
    private Animate animate;
    private SaveRLEDialog save;
    DynamicRLEParser rleParser;
    public DynamicBoard boardObj;
    public static Controller instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;
        titleName = "Game of Life";

        List<Point> plist = new ArrayList<Point>();

        drawBlankBoard();
        setID();
        initializeSliders();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), actionEvent -> nextGeneration());
        animate = new Animate(keyFrame);
        rule = new DynamicRule();
        animate.setSpeed(10);
    }

    /**
     * Sets the ID of the objects in this class for CSS styling.
     */
    private void setID() {
        nextGenButton.setId("nextGenButton");
        gridPane.setId("gridPane");
        scaleSlider.setId("scaleSlider");
        speedSlider.setId("speedSlider");
        startStopButton.setId("startButton");
        menuBar.setId("menuBar");
        resetButton.setId("resetButton");
    }

    /**
     * Initiates the sliders with min, max and start values
     */
    private void initializeSliders() {
        speedSlider.setMin(1);
        speedSlider.setMax(30);

        speedSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> animate.setAnimationRate(newValue.intValue()));

        scaleSlider.setValue(2);
        scaleSlider.setMin(0.3);
        scaleSlider.setMax(10);

        scaleSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            boardObj.setCellSize(newValue.doubleValue());
            boardObj.drawBoard();
        });
    }

    /**
     * Creates a "Change color" stage and sends the board colors to the Stage.
     */
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
            e.printStackTrace();
        }

        ColorStageController.instance.setCellColorPicker(boardObj.getcellColor());
        ColorStageController.instance.setBackgroundColorPicker(boardObj.getBackgroundColor());
    }

    /**
     * Method that set game board blank and draws it
     */
    private void drawBlankBoard() {
        boardObj = new DynamicBoard(canvas, 350, 250);
        boardObj.drawBoard();
    }

    /**
     * Applies the current rules on the game board and redraws it
     */
    public void nextGeneration() {

        rule.setCurrentBoard(boardObj.getBoard());

        rule.calculateBoardOfActiveCells();

        List<List<Byte>> tempArr = rule.applyBoardRules();

        boardObj.setBoard(tempArr);

        boardObj.drawBoard();


        counter++;
        Main.getStage().setTitle(titleName + " (" + counter + ")");


    }

    /**
     * Updates the title of the game window
     * @param newName
     */
    private void updateTitle(String newName) {
        Main.getStage().setTitle(newName);
        titleName = newName;
    }

    /**
     * Resets the game to its initial state and stops the animation
     */
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

    /**
     * Loads an RLE file from disk, adds the board to the boardobject and draws it
     * @throws IOException
     */
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

    /**
     * Creates a new blank board
     */
    public void newBlankAction() {
        boardObj.clearBoard();
        boardObj.clearInitialBoard();
        counter = 0;
        updateTitle("Game of Life");
    }

    /**
     * Adds or removes cell where the mouse is clicked depending on its previous state
     * @param event
     */
    public void mouseClicked(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseClickedOrDraggedOnBoard(p.x, p.y);
    }

    /**
     * Handles event if mouse is dragged and draws cells in its way
     * @param event
     */
    public void mouseDragged(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        boardObj.mouseClickedOrDraggedOnBoard(p.x, p.y);
    }

    /**
     * Exits the application
     */
    public void exitEvent() {
        System.exit(0);
    }

    /**
     * Changes the cell color
     * @param c Color of the cell
     */
    void changeCellColor(Color c) {
        boardObj.setCellColor(c);
    }

    /**
     * Changes the background color of the canvas
     * @param c Background color
     */
    void changeBackgroundColor(Color c) {
        boardObj.setBackgroundColor(c);
    }

    /**
     * Toggles the "Start/Stop" text of the Start/Stop button
     */
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

    /**
     * Toggles the grid using boolean value in the board class
     */
    public void toggleGrid() {

        if (boardObj.getGrid()) {
            boardObj.setGrid(false);
        } else {
            boardObj.setGrid(true);
        }

        boardObj.drawBoard();
    }

    /**
     * Creates the "GIF Export" stage and shows the Stage.
     */
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

    // Methods that apply the different rules and deals with the checking of the Rule menu.

    /**
     * Unchecks all of the CheckMenuItems by adding them to an ArrayList and iterating through the list.
     */
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

    /**
     * Sets the game rule to to Conways Rule.
     */
    public void selectConwaysLife() {
        unCheckAll();
        conwaysLife.setSelected(true);

        int[] b = {3};
        int[] s = {2,3};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Seeds.
     */
    public void selectSeeds() {
        unCheckAll();
        seeds.setSelected(true);

        int[] b = {2};
        int[] s = {};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Flock.
     */
    public void selectFlock() {
        unCheckAll();
        flock.setSelected(true);

        int[] b = {3};
        int[] s = {1,2};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to 2x2.
     */
    public void selectTwoBytwo() {
        unCheckAll();
        twoByTwo.setSelected(true);

        int[] b = {3,6};
        int[] s = {1,2,5};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Maze.
     */
    public void selectMaze() {
        unCheckAll();
        maze.setSelected(true);

        int[] b = {3};
        int[] s = {1,2,3,4,5};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Move.
     */
    public void selectMove() {
        unCheckAll();
        move.setSelected(true);

        int[] b = {3,6,8};
        int[] s = {2,4,5};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to HighLife.
     */
    public void selectHighLife() {
        unCheckAll();
        highLife.setSelected(true);

        int[] b = {3,6};
        int[] s = {2,3};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Mazectric.
     */
    public void selectMazectric() {
        unCheckAll();
        mazectric.setSelected(true);

        int[] b = {3};
        int[] s = {1,2,3,4};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Fredkin.
     */
    public void selectFredkin() {
        unCheckAll();
        fredkin.setSelected(true);

        int[] b = {1,3,5,7};
        int[] s = {0,2,4,6,8};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Replicator.
     */
    public void selectReplicator() {
        unCheckAll();
        replicator.setSelected(true);

        int[] b = {1,3,5,7};
        int[] s = {1,3,5,7};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Day & Night.
     */
    public void selectDayAndNight() {
        unCheckAll();
        dayAndNight.setSelected(true);

        int[] b = {3,6,7,8};
        int[] s = {3,4,6,7,8};

        Rule.setRules(s,b);
    }

    /**
     * Sets the game rule to Life without Death.
     */
    public void selectLifeWithoutDeath() {
        unCheckAll();
        lifeWithoutDeath.setSelected(true);

        int[] b = {3};
        int[] s = {0,1,2,3,4,5,6,7,8};

        Rule.setRules(s,b);
    }

}

