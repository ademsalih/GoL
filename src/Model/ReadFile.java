package Model;


import java.io.*;
import java.net.URL;


import javafx.stage.FileChooser;

/**
 * Small help class that reads files into the project
 */
public class ReadFile extends FileHandling{

    /**
     * Method for reading file from URL
     *
     * @param urlString URL to the file
     * @return A BufferedReader containing the RLE file
     * @throws IOException
     */
    public static BufferedReader readFileFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br;
    }

    /**
     * Reads a file from disk
     * @return BufferedReader containing the RLE file
     */
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
            System.err.println("Unable to read file");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Opens the select file dialog window and returns the chosen file as a File object.
     * @return File containing the filepath to the chosen file.
     */
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
