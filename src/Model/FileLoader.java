package Model;

/**
 * Created by patrikkvarmehansen on 03/03/17.
 */
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import test.Main;

public class FileLoader {

    //Support method for reading files
    public static void readFile() {
        try {
            FileReader fr = new FileReader(openFile());
            BufferedReader br = new BufferedReader(fr);
            //This is meant as a visual test of the method.
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("The file path is invalid");
            e.printStackTrace();
        }

    }

    public void readGameBoardFromDisk(File file) throws IOException {


    }

    public static File openFile () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .rle file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RLE file", "*.rle")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        return selectedFile;
    }




}
