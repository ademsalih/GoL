package View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Created by patrikkvarmehansen on 09/02/17.
 *
 * This class draws to the canvas.
 */
public class Graphic {

    //Method that sets the color for the cells.
    public void setColor () {
    }

    //Takes cell position and draws a square in a given size.
    public static void drawCell (GraphicsContext gc, int x, int y, int size) {
        gc.fillRect(x, y, size, size);
    }

}
