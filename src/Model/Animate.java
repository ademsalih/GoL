package Model;

import Controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Animate {

    public int speed;
    public int generations;
    public Timeline timeline;
    public boolean status;

    public Animate(int speed) {
        this.speed = speed;
        this.generations = Animation.INDEFINITE;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(speed), ae -> Controller.instance.nextGeneration() ));
        this.status = false;
        timeline.setCycleCount(generations);
    }

    public void setAnimationStatus(boolean status) {
        this.status = status;
    }

    public void getAnimationStatus() {
        System.out.println(this.status);
    }

    public void startStopButtonAction() {

        if ( timeline.getStatus() == Animation.Status.STOPPED ) {

            timeline.play();

            System.out.println("RUNNING");

            switchToStop();

        } else if ( timeline.getStatus() == Animation.Status.RUNNING) {

            System.out.println("STOPPED");

            timeline.stop();
            switchToStart();

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
