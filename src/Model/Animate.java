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

    public Animate() {
        this.speed = 1000;
        this.animationRate = 1;
        this.generations = Animation.INDEFINITE;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(speed), ae -> Controller.instance.nextGeneration() ));
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

    public void getAnimationStatus() {
        System.out.println(this.status);
    }

    public void startStopButtonAction() {

        if ( timeline.getStatus() == Animation.Status.STOPPED ) {
            startAnimation();

        } else if ( timeline.getStatus() == Animation.Status.RUNNING) {
            stopAnimation();
        }

    }

    public void startAnimation() {
        timeline.play();
        switchToStop();
    }

    public void stopAnimation() {
        timeline.stop();
        switchToStart();
    }

    public void switchToStop() {
        Controller.instance.getStartStopButton().setText("Stop");
        Controller.instance.getStartStopButton().setId("stopButton");
    }

    public void switchToStart() {
        Controller.instance.getStartStopButton().setText("Start");
        Controller.instance.getStartStopButton().setId("startButton");
    }
}
