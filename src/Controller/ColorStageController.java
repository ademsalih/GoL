package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ademsalih on 17.03.2017.
 */
/*public class ColorStageController implements Initializable {

    @FXML private ColorPicker cellColorPicker;
    @FXML private ColorPicker backgroundColorPicker;

    public static ColorStageController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
    }

    public void okButtonAction() {
        Controller.instance.stage.close();
    }

    public void applyButtonAction() {

        Controller.instance.changeCellColor( cellColorPicker.getValue() );
        Controller.instance.changeBackgroundColor( backgroundColorPicker.getValue() );

        Controller.instance.boardObj.drawBoardWithGrid();
    }

    public void setCellColorPicker(Color c) {
        cellColorPicker.valueProperty().setValue(c);
    }

    public void setBackgroundColorPicker(Color c) {
        backgroundColorPicker.valueProperty().setValue(c);
    }
}*/
