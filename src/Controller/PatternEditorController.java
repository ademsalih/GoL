package Controller;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PatternEditorController implements Initializable {

    public static PatternEditorController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
    }
}
