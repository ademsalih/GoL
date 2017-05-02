package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class draws rectangles and fills them with color on a point.
 */

public class Point {
    public double x,y;

    public double xOffset;
    public double yOffset;

    public void setXOffset(int x) {
        this.xOffset = x;
    }

    public void setYOffset(int y) {
        this.yOffset = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void draw(GraphicsContext gc, Color drawColor, double cellSize) {
        gc.setFill(drawColor);
        gc.fillRect(xOffset + x, yOffset + y, xOffset + cellSize, yOffset + cellSize);
    }
}
