package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ademsalih on 17.03.2017.
 */
public class ColorStageController implements Initializable {

    @FXML private ColorPicker cellColorPicker;
    @FXML private ColorPicker backgroundColorPicker;
    @FXML private GridPane gridPane;
    @FXML private Label cellColorLabel;
    @FXML private Label backgroundColorLabel;
    @FXML private Button closeButton;


    public static ColorStageController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        setID();
        initializeColorPickers();
    }

    public void setID() {
        gridPane.setId("gridPane");
        cellColorLabel.setId("cellColorLabel");
        backgroundColorLabel.setId("backgroundColorLabel");
        closeButton.setId("closeButton");
        cellColorPicker.setId("cellColorPicker");
        backgroundColorPicker.setId("backgroundColorPicker");
    }

    public void initializeColorPickers() {
        cellColorPicker.valueProperty().addListener((o, oldValue, newValue) -> {

            Controller.instance.changeCellColor( newValue );
            Controller.instance.boardObj.drawBoardWithGrid();
        });

        backgroundColorPicker.valueProperty().addListener((o, oldValue, newValue) -> {

            Controller.instance.changeBackgroundColor( newValue );
            Controller.instance.boardObj.drawBoardWithGrid();
        });
    }

    public void closeButtonAction() {
        Controller.instance.stage.close();
    }


    public void setCellColorPicker(Color c) {
        cellColorPicker.valueProperty().setValue(c);
    }

    public void setBackgroundColorPicker(Color c) {
        backgroundColorPicker.valueProperty().setValue(c);
    }
}
