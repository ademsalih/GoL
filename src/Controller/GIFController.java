package Controller;

import Model.DynamicGIF;
import Model.GIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the GIF Export Stage. This class handles button actions
 * and TextField inputs in the GIF Export Stage.
 */

public class GIFController implements Initializable {

    //public GIF gif;
    public DynamicGIF gif;
    public String path;
    public Thread exportThread;
    public static GIFController instance;
    @FXML private Label url;
    @FXML private TextField fileNameField;
    @FXML private TextField generationsField;
    @FXML private TextField speedField;
    @FXML public ProgressBar progressBar;
    @FXML public Button exportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        genTextFieldRestrictions();
        speedFieldRestrictions();
    }

    // Method that restricts the user from typing letters or numbers above 99
    // in the generations TextField using RegEx.
    public void genTextFieldRestrictions() {

        generationsField.textProperty().addListener((obs, oldV, newVal) -> {

            if (!newVal.matches("\\d*")) {
                generationsField.setText(newVal.replaceAll("[^\\d]", ""));
            }

            if (!(newVal.length() < 4)) {
                generationsField.setText(oldV);
            }
        });
    }

    public void speedFieldRestrictions() {

        speedField.textProperty().addListener((obs, oldV, newVal) -> {

            if (!newVal.matches("\\d*")) {
                speedField.setText(newVal.replaceAll("[^\\d]", ""));
            }

            if (!(newVal.length() < 4)) {
                speedField.setText(oldV);
            }

        });
    }

    // Method for "Choose..." Button. Creates DirectoryChooser object and sets the new path.
    // Alerts the user if there is no path selected.
    public void chooseLocationAction() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File exportPath;

        try {
           exportPath  = directoryChooser.showDialog(Controller.instance.gifStage);
           path = exportPath.getAbsolutePath();
           url.setText(path);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("No location was selected.");
            alert.showAndWait();
        }

    }

    // Checks whether filename, generations or speed have been specified.
    public void checkIfDefaultValuesHaveChanged() {

        if (!fileNameField.getText().isEmpty()) {
            gif.setFileName(fileNameField.getText());
        }

        if (!generationsField.getText().isEmpty()) {
            gif.setGenerations(Integer.parseInt(generationsField.getText()));
        }

        if (!speedField.getText().isEmpty()) {
            gif.setSpeed(Integer.parseInt(speedField.getText()));
        }
    }

    // Method for "Export" Button. Instantiates GIF-object.
    public void exportButtonAction() throws Exception {

        //gif = new GIF(700,500, 20, path);
        gif = new DynamicGIF(700,500,15, path, Controller.instance.boardObj.getCellSize(), progressBar, Controller.instance.boardObj);

        checkIfDefaultValuesHaveChanged();

        gif.setCellColor(Controller.instance.boardObj.getcellColor());
        gif.setBackgroundColor(Controller.instance.boardObj.getBackgroundColor());

        gif.createGIF();
    }


    // Closes the GIF Export Stage when "Cancel" Button is clicked.
    public void cancelButtonAction() {

        if (gif != null) {
            gif.stop();
        }

        Controller.instance.gifStage.close();
    }

}
