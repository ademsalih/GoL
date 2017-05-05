package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class draws rectangles and fills them with a color on a point.
 */

public class Point {
    public double x,y;

    private double xOffset;
    private double yOffset;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Draws rectangles on a given points and fills them with a specified color
     * @param gc
     * @param drawColor
     * @param cellSize
     */
    public void draw(GraphicsContext gc, Color drawColor, double cellSize) {
        gc.setFill(drawColor);
        gc.fillRect(xOffset + x, yOffset + y, xOffset + cellSize, yOffset + cellSize);
    }
}
