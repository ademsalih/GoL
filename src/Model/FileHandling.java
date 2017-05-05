package Model;

import javafx.scene.control.Alert;

/**
 * Created by patrikkvarmehansen on 28/03/17.
 */
public class FileHandling {

    public static void alert (String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void alert (String text) {
        alert("An error occured", text);
    }

    public static void info (String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();

    }

    public static void info (String text) {
        info("Info", text);
    }

}
