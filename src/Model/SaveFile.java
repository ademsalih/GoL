package Model;

import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by patrikkvarmehansen on 22/03/17.
 */
public class SaveFile {

    private String fileName = "Testfil.Rle";
    private String name = "#N ";
    private String author = "#O ";
    private String comment = "#C ";
    private String xYRules = "x = 7, y = 6, rule = B3/S23";
    private String rleString = "5b2o$3obobo2$bobo2bo$o4bob$2o3bo!";
    private String completeFileString = fileName + "\n" + name + "\n" + author + "\n" + comment + "\n" + xYRules + "\n" + rleString;
            BufferedWriter toFile = null;

    public File createFilePath() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save pattern as RLE");
        File file = fc.showSaveDialog(null);
        return file;
    }

    public void createFile(File file) {
        try {
            toFile = new BufferedWriter(new FileWriter(file));
            toFile.write(completeFileString);
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

    public void testRun() {
        createFile(createFilePath());
    }


}
