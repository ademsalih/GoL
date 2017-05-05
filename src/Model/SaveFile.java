package Model;

import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Does the actual writing and saving of a file.
 * Uses a single String to write to the fiel.
 */

public class SaveFile extends FileHandling {

    private BufferedWriter toFile = null;

    /**
     * Prompts the user were to store and what to name it using the systems dialog window
     * @return File That represents the filepath to the file
     */
    private File createFilePath() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save pattern as RLE");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("RLE file", "*.rle")
        );
        return fc.showSaveDialog(null);
    }

    /**
     * Creates a file with BufferedWriter at the filepath specified and adds a single String it.
     * @param file The filepath where the file should be stored.
     * @param txt The String that the BufferedWriter adds to the file.
     */
    private void createFile(File file, String txt) {
        try {
            toFile = new BufferedWriter(new FileWriter(file));
            toFile.write(txt);
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

    /**
     * Method that starts the save process
     * @param txt String holding the text that should be added to the file.
     */
    public void saveFile(String txt) {
        createFile(createFilePath(), txt);
    }


}
