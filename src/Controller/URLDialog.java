package Controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by patrikkvarmehansen on 01/05/17.
 */
public class URLDialog {

    private String url;

    /**
     * Sets up the dialogbox
     */
    public void showStage() {
        Stage stage = new Stage();
        stage.setTitle("Open URL");
        stage.setWidth(300);

        // Simple JavaFX setup. Not done with fxml since it's pretty minimal
        VBox root = new VBox();
        Scene scene = new Scene(root);
        TextField urlField = new TextField();
        Button button = new Button("Open");
        root.getChildren().addAll(urlField, button);
        stage.setScene(scene);

        // Executed when button is pressed
        button.setOnAction(e -> {
            url = urlField.getText();
            stage.close();
        });


        stage.showAndWait();
    }

    /**
     * Returns the URL-String from the textfield.
     * @return - String representing what should be an url leading to a RLE-pattern.
     */
    public String getURL() {
        if (!url.contains("http")) {
            return "http://"+url;
        }
        return url;
    }

}
