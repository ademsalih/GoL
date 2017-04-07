package Model;

/**
 * Created by patrikkvarmehansen on 03/03/17.
 */
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import javafx.stage.FileChooser;

public class ReadFile extends FileHandling{

    //TODO URL-file-opener

    public void readFileFromUrl() throws IOException {

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

    public void readGameBoardFromDisk(File file) throws IOException {


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
