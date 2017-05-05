package Model;

import Controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Animation class for handling animation of the pattern. Main method for
 * stopping and starting the animation is "startStopButtonAction()" which
 * should be assigned to the Start/Stop Button in GUI.
 */

public class Animate {

    private int speed;
    private boolean status;
    private int generations;
    private KeyFrame keyFrame;
    private int animationRate;
    private Timeline timeline;

    public Animate(KeyFrame keyFrame) {
        this.keyFrame = keyFrame;
        this.speed = 1000;
        this.animationRate = 1;
        this.generations = Animation.INDEFINITE;
        this.timeline = new Timeline(keyFrame);
        this.status = false;
        timeline.setCycleCount(generations);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    /**
     * Sets the new animation rate and the rate of the Timeline.
     * @param newRate
     */
    public void setAnimationRate(int newRate) {
        this.animationRate = newRate;
        timeline.setRate(newRate);
    }

    public int getAnimationRate() {
        return this.animationRate;
    }

    public void setAnimationStatus(boolean status) {
        this.status = status;
    }

    public boolean getAnimationStatus() {
        return this.status;
    }

    /**
     * Stops the animation if its running or starts the animation if its stopped.
     * Updates the status boolean value accordingly.
     */
    public void startStopButtonAction() {

        if ( timeline.getStatus() == Animation.Status.STOPPED ) {
            startAnimation();
            status = false;

        } else if ( timeline.getStatus() == Animation.Status.RUNNING) {
            stopAnimation();
            status = true;
        }

    }

    /**
     * Starts the timeline animation.
     */
    public void startAnimation() {
        timeline.play();
    }

    /**
     * Stops the timeline animation.
     */
    public void stopAnimation() {
        timeline.stop();
    }


}
