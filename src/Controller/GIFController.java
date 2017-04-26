package Controller;

import Model.GIF;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the GIF Export Stage. This class handles button actions
 * and TextField inputs in the GIF Export Stage.
 */

public class GIFController implements Initializable {

    public GIF gif;
    public String path;
    public static GIFController instance;
    @FXML private Label url;
    @FXML private TextField fileNameField;
    @FXML private TextField generationsField;
    @FXML private Slider speedSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;
        genTextFieldRestrictions();

    }

    // Method that restricts the user from typing letters or numbers above 99
    // in the generations TextField using RegEx.
    public void genTextFieldRestrictions() {

        generationsField.textProperty().addListener((obs, oldV, newVal) -> {

            if (!newVal.matches("\\d*")) {
                generationsField.setText(newVal.replaceAll("[^\\d]", ""));
            }

            if (!(newVal.length() <3)) {
                generationsField.setText(oldV);
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

    // Method for "Export" Button. Instantiates GIF-objects and checks whether
    // filename or generations have been specified. If not it uses default values.
    public void exportButtonAction() throws Exception {

        gif = new GIF(700,500, 20, path);

        if (!fileNameField.getText().isEmpty()) {

            gif.setFileName(fileNameField.getText());
        }

        if (!generationsField.getText().isEmpty()) {

            gif.setGenerations(Integer.parseInt(generationsField.getText()));
        }

        gif.createGIF();

    }

    // Closes the GIF Export Stage when "Cancel" Button is clicked.
    public void cancelButtonAction() {
        Controller.instance.gifStage.close();
    }



}
