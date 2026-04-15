package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.color;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.proximitySensor;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorDetection {
    public static double distance = 2;
    public float red = 0;
    public float green = 0;
    public float blue = 0;
    public static String ball1 = "Waiting for artifact...", ball2 = "Waiting for artifact...", ball3 = "Waiting for artifact...";
    public double greenBall = 0, purpleBall = 0;

    public ColorDetection(){
        color.enableLed(true);
    }
    public void update(){
        if (isBallInStorage() && !Storage.IsStorageSpinning()) {
            red = color.red();
            blue = color.blue();
            green = color.green();
            greenBall = distance(red, green, blue, 0, 255, 0);
            purpleBall = distance(red, green, blue, 175, 0, 175);
        }

        if (isBallInStorage() && Storage.state == Storage.State.BALL1){
            ball3 = ball2;
            ball2 = ball1;
        }
        if (isBallInStorage() && Storage.state == Storage.State.BALL2){
            ball3 = ball2;
            ball2 = ball1;
        }

        if (isBallInStorage() && Storage.state == Storage.State.BALL3){
            ball3 = ball2;
            ball2 = ball1;
        }
        if (purpleBall<=greenBall){
            ball1 = "Green";
        }
        else {
            ball1 = "Purple";
        }
        if (Storage.state == Storage.State.RESET){
            ball1 = "Waiting for artifact..."; ball2 = "Waiting for artifact..."; ball3 = "Waiting for artifact...";
        }
    }
    public static boolean isBallInStorage(){
        return !proximitySensor.getState() || color.getDistance(DistanceUnit.MM)<10;
    }
    public float distance(float r1, float g1 , float b1, float r2, float g2, float b2) {
        return (float)Math.sqrt( (r1-r2)*(r1-r2) + (b1-b2)*(b1-b2) + (g1-g2)*(g1-g2));
    }
}
