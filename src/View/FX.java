package View;/**
 * Created by patrikkvarmehansen on 09/02/17.
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FX extends Application {


    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");
        Group root = new Group();
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicContext.drawCell(gc, 2, 5, 5);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 100, 100));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

