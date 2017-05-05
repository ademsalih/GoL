package Model;

import Controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class handles everything with Animation and buttons that control the animation.
 */

public class Animate {

    public int speed;
    public int generations;
    public Timeline timeline;
    public boolean status;
    public KeyFrame keyFrame;
    public int animationRate;

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
     * ADEM SKAL SKRIVE HER
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
     * HEI ADEEEM, SKRIV NOE HER
     */
    public void startAnimation() {
        timeline.play();
    }

    /**
     * HER OGSÅ ADEM
     */
    public void stopAnimation() {
        timeline.stop();
    }


}
