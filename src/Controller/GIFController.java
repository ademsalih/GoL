package Controller;

import Model.GIF;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GIFController implements Initializable {

    GIF gif;
    String path;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void chooseLocationAction() {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        File exportPath;

        try {

           exportPath  = directoryChooser.showDialog(Controller.instance.gifStage);
           path = exportPath.getAbsolutePath();

        } catch (Exception e) {
            System.out.println("no location");
        }


    }


    public void exportButtonAction() throws Exception {

        gif = new GIF(700,500, 10, path);

        gif.createGIF();

    }





}
