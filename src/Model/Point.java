package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class draws rectangles and fills them with color on a point.
 */
public class Point {
    public double x,y;

    public void draw(GraphicsContext gc, Color drawColor, double cellSize) {
        gc.setFill(drawColor);
        gc.fillRect(x, y, cellSize, cellSize);
    }
}
