package Model;


import Controller.Controller;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.scene.control.Alert;
import lieng.GIFWriter;
import java.awt.*;

public class GIF {

    public int width;
    public int height;
    public int generations;
    public int milliseconds;
    public int cellSize;
    public int xCounter;
    public int yCounter;
    public byte[][] gifBoard;
    public GIFWriter gwriter;
    public Rule gifRule;
    public String path;
    public String filename;
    public Color cellColor;
    public Color backgroundColor;
    public Board importedBoard;


    public GIF (int width, int height, int generations, String path) throws Exception {
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.path = path;
        this.filename = "/export.gif";
        this.milliseconds = 50;
        this.cellSize = 2;
        this.cellColor = Color.BLACK;
        this.backgroundColor = Color.WHITE;
        this.importedBoard = Controller.instance.boardObj;
    }

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

    public void noLocationSelectedAlert() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("GIF Export");
        alert.setHeaderText(null);
        alert.setContentText("Select export location!");
        alert.showAndWait();

    }

    private void copyImportedBoardToGIFBoard() {

        importedBoard = Controller.instance.boardObj;

        gifBoard = new byte[importedBoard.board.length][importedBoard.board[0].length];

        for (int y = 0; y < gifBoard.length; y++) {
            for (int x = 0; x < gifBoard[0].length; x++) {

                if (importedBoard.board[y][x] == 1) {
                    gifBoard[y][x] = 1;
                } else {
                    gifBoard[y][x] = 0;
                }
            }
        }

    }

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

    public void next() {

        gifRule = new Rule(gifBoard);
        gifBoard = gifRule.conwaysBoardRules();
    }


    public void writeGoLSequenceToGIF(GIFWriter writer, byte[][] board, int counter) throws Exception {

        if (counter == 0) {
            gwriter.close();
            exportFinishedAlert();
            return;
        }

        drawFrame();

        gwriter.insertCurrentImage();

        next();

        writeGoLSequenceToGIF(gwriter, gifBoard, counter - 1);

    }

    public void exportFinishedAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("GIF Export");

        alert.setHeaderText(null);

        alert.setContentText("The GIF was succesfully exported.");

        alert.showAndWait();
    }
}
