package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Narmatha on 03.03.2017.
 */
public class Point {
    public double x,y;

    public void draw(GraphicsContext gc, Color drawColor, double cellSize) {
        gc.setFill(drawColor);
        gc.fillRect(x, y, cellSize, cellSize);
    }
}
