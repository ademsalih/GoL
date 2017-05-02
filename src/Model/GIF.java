package Model;


import Model.StaticFiles.StaticBoard;
import Model.StaticFiles.StaticRule;
import javafx.scene.control.Alert;
import lieng.GIFWriter;
import java.awt.Color;

/**
 *  This class creates a gif-object in which you can specify width, height,
 *  generations and path for exporting. A gif created using the method "createGIF"
 *  and recursive method "writeGoLSequenceToGIF".
 */

public class GIF {

    private int width;
    private int height;
    private int generations;
    private int milliseconds;
    private int cellSize;
    private int xCounter;
    private int yCounter;
    private byte[][] gifBoard;
    private GIFWriter gwriter;
    private StaticRule gifStaticRule;
    private String path;
    private String filename;
    private Color cellColor;
    private Color backgroundColor;
    private StaticBoard importedStaticBoard;

    // Class constructor which creates object.
    public GIF (int width, int height, int generations, String path) throws Exception {
        //this.importedStaticBoard = Controller.instance.boardObj;
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.path = path;
        this.filename = "/export.gif";
        this.milliseconds = 100;
        this.cellSize = 2;
        this.cellColor = convertFXToAwtColor(importedStaticBoard.getcellColor());
        this.backgroundColor = convertFXToAwtColor(importedStaticBoard.getBackgroundColor());

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
            path+=filename;
            gwriter = new GIFWriter(width, height, path, milliseconds);
            writeGoLSequenceToGIF(gwriter, gifBoard, generations);

        } else {
            noLocationSelectedAlert();
        }

    }

    // Alerts the user if theres no export location selected.
    public void noLocationSelectedAlert() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("GIF Export");
        alert.setHeaderText(null);
        alert.setContentText("Select export location!");
        alert.showAndWait();

    }

    // Method used for copying the current board in the game to this class.
    private void copyImportedBoardToGIFBoard() {

        //importedStaticBoard = Controller.instance.boardObj;

        gifBoard = new byte[importedStaticBoard.board.length][importedStaticBoard.board[0].length];

        for (int y = 0; y < gifBoard.length; y++) {
            for (int x = 0; x < gifBoard[0].length; x++) {

                if (importedStaticBoard.board[y][x] == 1) {
                    gifBoard[y][x] = 1;
                } else {
                    gifBoard[y][x] = 0;
                }
            }
        }

    }

    // Method that draws the current state of the game to the gif sequence.
    public void drawFrame() {

        for (int y = 0; y < gifBoard.length; y++) {
            for (int x = 0; x < gifBoard[0].length; x++) {

                if (gifBoard[y][x] == 1) {

                    gwriter.fillRect(xCounter,(xCounter+cellSize)-1, yCounter, (yCounter+cellSize)-1, cellColor);

                } else {
                    gwriter.fillRect(xCounter,(xCounter+cellSize)-1, yCounter, (yCounter+cellSize)-1, backgroundColor);
                }
                xCounter += cellSize;

                if (gifBoard[0].length - 1 == x) {
                    xCounter = 0;
                    yCounter+=cellSize;
                }
            }
        }
        yCounter = 0;
    }

    // Iterates the game using the Model.StaticRule class.
    public void next() {

        gifStaticRule = new StaticRule(gifBoard);
        gifBoard = gifStaticRule.conwaysBoardRules();
    }

    // Adds the states of the game to the gif object using rail recursion until base case is fulfilled.
    public void writeGoLSequenceToGIF(GIFWriter writer, byte[][] board, int counter) throws Exception {

        // Base case (i.e. stop condition)
        if (counter == 0) {
            gwriter.close();
            exportFinishedAlert();
            return;
        }

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
}
