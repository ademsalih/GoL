package Model;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by patrikkvarmehansen on 22/03/17.
 */

public class SaveFile extends FileHandling {

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
            alert("Error writing to disk", "There was an error while trying to write to the disk");
        } finally {
            try {
                if (toFile != null) {
                    toFile.close();
                }
            } catch (IOException e2) {
                alert("Error writing to disk", "There was an error while trying to write to the disk");
            }
        }
    }

    public void saveFile(byte[][] board) {
        createFile(createFilePath(), board);
    }


}
