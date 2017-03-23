package Model;

import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by patrikkvarmehansen on 22/03/17.
 */
public class SaveFile {

    /*private String fileName = "Testfil.Rle";
    private String name = "#N ";
    private String author = "#O ";
    private String comment = "#C ";
    private String xYRules = "x = 7, y = 6, rule = B3/S23";
    private String rleString;
    private String completeFileString = fileName + "\n" + name + "\n" + author + "\n" + comment + "\n" + xYRules + "\n";*/
    BufferedWriter toFile = null;

    public File createFilePath() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save pattern as RLE");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("RLE file", "*.rle")
        );
        File file = fc.showSaveDialog(null);
        return file;
    }

    public void createFile(File file, byte[][] board) {
        ParseToRLE ptr = new ParseToRLE();
        try {
            toFile = new BufferedWriter(new FileWriter(file));
            toFile.write(ptr.extractingRLE(board));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (toFile != null) {
                    toFile.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void saveFile(byte[][] board) {
        createFile(createFilePath(), board);
    }


}
