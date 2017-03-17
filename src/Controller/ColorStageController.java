package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ademsalih on 17.03.2017.
 */
public class ColorStageController implements Initializable {

    @FXML private ColorPicker cellColorPicker;
    @FXML private ColorPicker backgroundColorPicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void okButtonAction() {
        Controller.instance.stage.close();
    }

    public void applyButtonAction() {

        Controller.instance.changeCellColor( cellColorPicker.getValue() );
        Controller.instance.changeBackgroundColor( backgroundColorPicker.getValue() );

        Controller.instance.boardObj.drawBoardWithGrid();
    }
}
