package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    public static Stage getStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws Exception {

        mainStage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("/View/FXML/layout.fxml"));

        Scene scene = new Scene(root, 700, 569);
        scene.getStylesheets().add("View/CSS/stylesheet.css");
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);

























    }

}
