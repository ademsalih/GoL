package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    @FXML private Canvas graphics;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider sizeSlider;

    private boolean isStarted;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // hjelpemetode som tegner grafikk til 'canvas' området i GUI
    private void draw() {
        GraphicsContext gc = graphics.getGraphicsContext2D();
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
