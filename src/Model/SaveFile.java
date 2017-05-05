package Model;

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
    String name;
    String author;
    String comment;

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public File createFilePath() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save pattern as RLE");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("RLE file", "*.rle")
        );
        File file = fc.showSaveDialog(null);
        return file;
    }

    public void createFile(File file, String txt) {
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

    public void saveFile(String txt) {
        createFile(createFilePath(), txt);
    }


}
