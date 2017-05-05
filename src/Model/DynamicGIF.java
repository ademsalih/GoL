package Model;

import Model.DynamicFiles.DynamicBoard;
import Model.DynamicFiles.DynamicRule;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import lieng.GIFWriter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import Controller.Controller;

/**
 * Created by ademsalih on 01.05.2017.
 */
public class DynamicGIF {

    private int width;
    private int height;
    private int cellSize;
    private int xCounter;
    private int yCounter;
    private int generations;
    private int milliseconds;
    private String path;
    private String filename;
    private Color cellColor;
    private GIFWriter gwriter;
    private Color backgroundColor;
    private List<List<Byte>> gifBoard;
    private List<List<Byte>> gifCheckSizeBoard;
    private DynamicBoard importedDynamicBoard;
    private DynamicRule gifDynamicRule = new DynamicRule();
    private Thread exportThread;
    private ProgressBar pbar;
    private double progress;
    private int currentGeneration;
    private int maxHeight;
    private int maxWidth;

    public DynamicGIF (int width, int height, int generations, String path, double cellSize, ProgressBar progressBar, DynamicBoard board) throws Exception {
        this.importedDynamicBoard = board;
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.path = path;
        this.filename = "/export.gif";
        this.milliseconds = 100;
        this.cellSize = (int) Math.ceil(cellSize);
        this.cellColor = Color.WHITE;
        this.backgroundColor = Color.BLACK;
        this.pbar = progressBar;
        this.progress = 0.0;
        this.currentGeneration = 0;
    }

    public void setCellColor(javafx.scene.paint.Color color) {
        this.cellColor = convertFXToAwtColor(color);
    }

    public void setBackgroundColor(javafx.scene.paint.Color color) {
        this.backgroundColor = convertFXToAwtColor(color);
    }

    public int getCellSize() {
        return this.cellSize;
    }

    // Takes in JavaFX Color Object and returns it as Java Awt Color object.
    public Color convertFXToAwtColor(javafx.scene.paint.Color fxColor) {

        return new Color(
                (float) fxColor.getRed(),
                (float) fxColor.getGreen(),
                (float) fxColor.getBlue()
        );
    }

    public void setFileName(String filename) {
        this.filename = "/" + filename + ".gif";
    }

    public String getName() {
        return this.filename;
    }

    public void setGenerations(int gen) {
        this.generations = gen;
    }

    public int getGenerations() {
        return this.generations;
    }

    public void setSpeed(int newSpeed) {
        this.milliseconds = newSpeed;
    }

    public int getSpeed() {
        return this.milliseconds;
    }

    // Method that copies the values from the board, and creates gif object and exports it.
    public void createGIF() throws Exception {

        copyImportedBoardToGIFBoard();

        if (path != null) {

            exportThread = new Thread(() -> {

                this.width = cellSize * maxWidth;
                this.height = cellSize * maxHeight;
                path+=filename;

                try {
                    gwriter = new GIFWriter(width, height, path, milliseconds);
                    writeGoLSequenceToGIF(gwriter, gifBoard, generations);
                } catch (Exception e) {

                }
            });

            exportThread.start();

        } else {
            noLocationSelectedAlert();
        }

    }


    public void notifyIfExpanded() {
        int i = 0;

        while (i < generations) {
            nextCheckSize();
            i++;
        }

        maxHeight = gifCheckSizeBoard.size();
        maxWidth = gifCheckSizeBoard.get(0).size();
    }

    // Method used for copying the current board in the game to this class.
    private void copyImportedBoardToGIFBoard() {

        gifCheckSizeBoard = new ArrayList<>();
        gifBoard = new ArrayList<>();

        for (int i = 0; i <  importedDynamicBoard.board.size(); i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < importedDynamicBoard.board.get(0).size(); j++) {
                row.add((byte) 0);
            }
            this.gifBoard.add(row);
        }

        for (int y = 0; y < gifBoard.size(); y++) {
            for (int x = 0; x < gifBoard.get(0).size(); x++) {

                if (importedDynamicBoard.board.get(y).get(x) == 1) {
                    gifBoard.get(y).set(x, (byte) 1);
                } else {
                    gifBoard.get(y).set(x, (byte) 0);
                }
            }
        }

        for (int i = 0; i <  importedDynamicBoard.board.size(); i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < importedDynamicBoard.board.get(0).size(); j++) {
                row.add((byte) 0);
            }
            this.gifCheckSizeBoard.add(row);
        }

        for (int y = 0; y < gifCheckSizeBoard.size(); y++) {
            for (int x = 0; x < gifCheckSizeBoard.get(0).size(); x++) {

                if (importedDynamicBoard.board.get(y).get(x) == 1) {
                    gifCheckSizeBoard.get(y).set(x, (byte) 1);
                } else {
                    gifCheckSizeBoard.get(y).set(x, (byte) 0);
                }
            }
        }

        notifyIfExpanded();
    }

    // Method that draws the current state of the game to the gif sequence.

    public void drawFrame() {

        gwriter.fillRect(0,width-1,0,height-1,backgroundColor);

        for (int y = 0; y < gifBoard.size(); y++) {
            for (int x = 0; x < gifBoard.get(0).size(); x++) {

                if (gifBoard.get(y).get(x) == 1) {

                    gwriter.fillRect(xCounter,(xCounter+cellSize)-1, yCounter, (yCounter+cellSize)-1, cellColor);

                } else {

                    gwriter.fillRect(xCounter,(xCounter+cellSize)-1, yCounter, (yCounter+cellSize)-1, backgroundColor);
                }
                xCounter += cellSize;

                if (x == (gifBoard.get(0).size() - 1 )) {
                    xCounter = 0;
                    yCounter+=cellSize;
                }
            }
        }
        yCounter = 0;
    }

    // Iterates the game using the Model.StaticRule class.
    public void next() {
        gifDynamicRule.setCurrentBoard(this.gifBoard);
        gifDynamicRule.calculateBoardOfActiveCells();
        gifBoard = gifDynamicRule.applyBoardRules();
    }

    public void nextCheckSize() {
        gifDynamicRule.setCurrentBoard(this.gifCheckSizeBoard);
        gifDynamicRule.calculateBoardOfActiveCells();
        gifCheckSizeBoard = gifDynamicRule.applyBoardRules();
    }

    public void updateProgress(int currentGen) {
        this.progress =  (double) currentGen / (double) generations;
    }

    public void setCurrentGeneration (int newGen) {
        this.currentGeneration = newGen;
    }

    public void updatePBar(double pBarVal) {
        this.pbar.setProgress(pBarVal);
    }

    // Adds the states of the game to the gif object using rail recursion until base case is fulfilled.
    public void writeGoLSequenceToGIF(GIFWriter writer, List<List<Byte>> board, int counter) throws Exception {

        // Base case (i.e. stop condition)
        if (counter == 0) {
            gwriter.close();

            Platform.runLater(() -> {
                exportFinishedAlert();
                pbar.setProgress(0.0);
            });
            return;
        }

        setCurrentGeneration(generations - counter + 1);

        updateProgress(currentGeneration);

        updatePBar(progress);

        // Draws the current board using "gwriter" method of GIFWriter class.
        drawFrame();

        // Adds the drawn image to the gif sequence.
        gwriter.insertCurrentImage();

        // Iterates the board once according to the rules.
        next();

        // Recursive call to writeGoLSwquenceToGIF.
        writeGoLSequenceToGIF(gwriter, gifBoard, counter - 1);

    }

    // Alerts the user that the gif has finished exporting.
    public void exportFinishedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GIF Export");
        alert.setHeaderText(null);
        alert.setContentText("The GIF was succesfully exported.");
        alert.showAndWait();
    }

    // Alerts the user if theres no export location selected.
    public void noLocationSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("GIF Export");
        alert.setHeaderText(null);
        alert.setContentText("Select export location!");
        alert.showAndWait();
    }

    public void stop() {
        exportThread.stop();
    }

}
