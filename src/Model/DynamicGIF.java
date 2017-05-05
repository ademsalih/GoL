package Model;

import Model.DynamicFiles.DynamicBoard;
import Model.DynamicFiles.DynamicRule;
import javafx.scene.control.Alert;
import javafx.scene.paint.*;
import lieng.GIFWriter;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
    private DynamicBoard importedDynamicBoard;
    private DynamicRule gifDynamicRule = new DynamicRule();
    private Thread exportThread;

    public DynamicGIF (int width, int height, int generations, String path, double cellSize) throws Exception {
        this.importedDynamicBoard = Controller.instance.boardObj;
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.path = path;
        this.filename = "/export.gif";
        this.milliseconds = 100;
        this.cellSize = (int) Math.ceil(cellSize);
        this.cellColor = Color.WHITE;
        this.backgroundColor = Color.BLACK;
    }

    public void setCellColor(javafx.scene.paint.Color color) {
        this.cellColor = convertFXToAwtColor(color);
    }

    public void setBackgroundColor(javafx.scene.paint.Color color) {
        this.cellColor = convertFXToAwtColor(color);
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

    /*public boolean boardIsTooBig(List<List<Byte>> list) {

        System.out.println("Board size: " + list.get(0).size());

        if (list.get(0).size() > width/cellSize) {
            return true;
        }
        return false;
    }*/

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

        /*this.exportThread = new Thread(() -> {

        });*/

        copyImportedBoardToGIFBoard();

        System.out.println("Width: " + gifBoard.get(0).size());
        System.out.println("Height: " + gifBoard.size());
        System.out.println("Cellsize: " + cellSize);

        System.out.println("Writing cap: " + width/cellSize);
        System.out.println("Writing cap: " + height/cellSize);

        if (path != null) {
            path+=filename;
            gwriter = new GIFWriter(width, height, path, milliseconds);
            writeGoLSequenceToGIF(gwriter, gifBoard, generations);

        } else {
            noLocationSelectedAlert();
        }

    }

    // Method used for copying the current board in the game to this class.
    private void copyImportedBoardToGIFBoard() {

        gifBoard = new ArrayList<List<Byte>>();

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

    }

    // Method that draws the current state of the game to the gif sequence.
    public void drawFrame() {

        //gwriter.fillRect(0,width-1,0,height-1, backgroundColor);


        if (width/cellSize > gifBoard.get(0).size()) {
            System.out.println("Stort nok");
        } else {
            System.out.println("ikke stort nok");
        }

        for (int y = 0; y < gifBoard.size(); y++) {
            for (int x = 0; x <= gifBoard.get(0).size(); x++) {

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

    // Adds the states of the game to the gif object using rail recursion until base case is fulfilled.
    public void writeGoLSequenceToGIF(GIFWriter writer, List<List<Byte>> board, int counter) throws Exception {

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

    // Alerts the user if theres no export location selected.
    public void noLocationSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("GIF Export");
        alert.setHeaderText(null);
        alert.setContentText("Select export location!");
        alert.showAndWait();
    }

    public void boardTooBigAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("GIF Export");
        alert.setHeaderText(null);
        alert.setContentText("This pattern is too big for exporting!");
        alert.showAndWait();
    }
}
