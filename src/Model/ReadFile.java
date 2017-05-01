package Model;

/**
 * Created by patrikkvarmehansen on 03/03/17.
 */
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.stage.FileChooser;

public class ReadFile extends FileHandling{

    //TODO URL-file-opener

    public static BufferedReader readFileFromUrl(String filePath) throws IOException {
        URL url = new URL(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br;

    }

    //Support method for reading files
    public static BufferedReader readFileFromDisk() {
        try {
            File file = openFile();
            if (file != null) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                return br;
            }
            else {
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static File openFile () {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open .rle file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("RLE file", "*.rle")
            );
            File selectedFile = fileChooser.showOpenDialog(null);

            return selectedFile;
        } catch (Exception e) {
            e.printStackTrace();
            alert("Error opening file", "There was an error while trying to open the file.");
            return null;
        }
    }




}
