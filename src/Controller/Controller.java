package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Narmatha on 09.02.2017.
 */
public class Controller implements Initializable {

    // interne objekter relatert til GUI
    @FXML private MenuBar menuBar;
    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider sizeSlider;
    @FXML private Slider speedSlider;
    @FXML private Button startButton;
    @FXML private Button nextButton;
    @FXML private Button previousButton;
    @FXML private Button clearButton;

    private boolean isStarted;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // hjelpemetode som tegner grafikk til 'canvas' området i GUI
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
    }

    protected void startStopBtnClicked(){

    }

    protected void stop(){

    }

    protected void start(){

    }

    protected void nextBtnClicked(){

    }

    protected void speedChange(){

    }

    protected void zoomChange(){

    }

    protected void Shapeselected(String a){

    }

    protected void clearBoard(){

    }


}
