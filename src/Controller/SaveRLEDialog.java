package Controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SaveRLEDialog {

    private String name;
    private String author;
    private String comment;
    private boolean isClosed;


    /**
     * Sets up the dialogbox
     */
    public void showStage() {
        Stage stage = new Stage();
        stage.setTitle("Save pattern as rle");
        stage.setWidth(350);

        // Simple JavaFX setup. Not done with fxml since it's pretty minimal
        VBox root = new VBox();
        Scene scene = new Scene(root);

        // Boxes
        HBox nameData = new HBox();
        HBox authorData = new HBox();
        HBox commentData = new HBox();

        // TextFields
        Text nameText = new Text("Name:");
        Text authorText = new Text("Author:");
        Text commentText = new Text("Comment:");
        TextField nameField = new TextField();
        TextField authorField = new TextField();
        TextArea commentArea = new TextArea();

        nameData.getChildren().addAll(nameText, nameField);
        authorData.getChildren().addAll(authorText, authorField);
        commentData.getChildren().addAll(commentText, commentArea);


        // Button
        Button button = new Button("Save");

        root.getChildren().addAll(nameData, authorData, commentData, button);
        stage.setScene(scene);

        // Executed when button is pressed
        button.setOnAction(e -> {
            name = nameField.getText();
            author = authorField.getText();
            comment = commentArea.getText();
            isClosed = false;
            stage.close();
        });

        stage.setOnCloseRequest(e -> {
            isClosed = true;
        });

        stage.showAndWait();
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
