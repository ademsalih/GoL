package Model.DynamicFiles;

import Model.PopUps;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import lieng.GIFWriter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>DynamicGIF</h1>
 * <h3>Dynamic GIF objects which can export dynamic boards that expand.</h3>
 * <p>The Dynamic Board pattern from the main window is copied over to local Dynamic Board. That way when the pattern
 * in this class is iterating it does not affect the main pattern in the background. The gif is written with the
 * library GIFLib.jar which is provided for use in this class. The gif is written with a recursive method which iterates
 * as long as the generation variable is not zero.
 * This class contains variables and a Progress Bar for visual representation of the export process.</p>
 */

public class DynamicGIF {

    private int width;
    private int height;
    private String path;
    private int maxWidth;
    private int cellSize;
    private int xCounter;
    private int yCounter;
    private int maxHeight;
    private String filename;
    private Color cellColor;
    private int generations;
    private double progress;
    private int milliseconds;
    private ProgressBar pbar;
    private GIFWriter gwriter;
    private Thread exportThread;
    private Color backgroundColor;
    private int currentGeneration;
    private List<List<Byte>> gifBoard;
    private List<List<Byte>> gifCheckSizeBoard;
    private DynamicBoard importedDynamicBoard;
    private DynamicRule gifDynamicRule = new DynamicRule();

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

    /**
     * Converts JavaFX Color (javafx.scene.paint.Color) to AWT Color (java.awt.Color).
     *
     * @param fxColor
     * @return new AWT Color (java.awt.Color).
     */
    public Color convertFXToAwtColor(javafx.scene.paint.Color fxColor) {

        return new Color(
                (float) fxColor.getRed(),
                (float) fxColor.getGreen(),
                (float) fxColor.getBlue()
        );
    }

    /**
     * Sets the new filename if default value has been changed in GUI.
     * Adds "/" before and ".gif" after the filename so the export
     * URL is: locationSelectHere/filename.gif
     * @param filename
     */
    public void setFileName(String filename) {
        this.filename = "/" + filename + ".gif";
    }

    public String getFilename() {
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

    /**
     * Main method for exporting the pattern as a GIF file. Export only occurs if the path is selected.
     * If the path is not selected and Alert is sent to the user.
     * @throws Exception
     */
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
                    e.printStackTrace();
                }
            });

            exportThread.start();

        } else {
            PopUps.warning("GIF Export","Select export location!");
        }
    }

    /**
     * Checks if the pattern has evolved out of current dimensions
     * and resizes the GIF size accordingly.
     */
    public void checkIfExpanded() {
        int i = 0;

        while (i < generations) {
            nextCheckSize();
            i++;
        }

        maxHeight = gifCheckSizeBoard.size();
        maxWidth = gifCheckSizeBoard.get(0).size();
    }

    /**
     * Creates two 2D Lists which hold the pattern from the main window.
     */
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

        checkIfExpanded();
    }

    /**
     * Draws an image to the gif file by looping through the gifBoard.
     */
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

    /**
     * Calculates the next generation of the pattern which is being exported as a GIF file.
     */
    public void next() {
        gifDynamicRule.setCurrentBoard(this.gifBoard);
        gifDynamicRule.calculateBoardOfActiveCells();
        gifBoard = gifDynamicRule.applyBoardRules();
    }

    /**
     * Calculates the next generation of the pattern which is checks if the pattern exceeds the initial dimensions.
     */
    public void nextCheckSize() {
        gifDynamicRule.setCurrentBoard(this.gifCheckSizeBoard);
        gifDynamicRule.calculateBoardOfActiveCells();
        gifCheckSizeBoard = gifDynamicRule.applyBoardRules();
    }

    /**
     * Updates the progress double value with the exported percentage as a decimal value.
     * @param currentGen
     */
    public void updateProgress(int currentGen) {
        this.progress =  (double) currentGen / (double) generations;
    }

    public void setCurrentGeneration (int newGen) {
        this.currentGeneration = newGen;
    }

    /**
     * Sets the value of the Progress Bar in the GIF Export Menu.
     * @param pBarVal
     */
    public void updatePBar(double pBarVal) {
        this.pbar.setProgress(pBarVal);
    }


    private boolean stopRunning = false;


    /**
     * Recursively writes the pattern to the GIFWriter and updates the export progress by updating the Progress Bar.
     * Alerts user when the exporting process has finished.
     * @param writer
     * @param board
     * @param counter
     * @throws Exception
     */
    public void writeGoLSequenceToGIF(GIFWriter writer, List<List<Byte>> board, int counter) throws Exception {

        // Base case (i.e. stop condition)
        if (counter == 0) {
            gwriter.close();
            gwriter = null;
            Platform.runLater(() -> {
                PopUps.info("GIF Export","The GIF was succesfully exported.");
                pbar.setProgress(0.0);
            });
            return;
        }

        if (stopRunning) {
            return;
        }

        // Updates the current generation being written.
        setCurrentGeneration(generations - counter + 1);

        //Updates the progress decimal value.
        updateProgress(currentGeneration);

        // Updates the progressbar.
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

    /**
     * Stops the exporting progess only if the GIFWrtier object is initiated.
     */
    public void stop() {

        if (gwriter != null) {
            stopRunning = true;
        }

    }


}
