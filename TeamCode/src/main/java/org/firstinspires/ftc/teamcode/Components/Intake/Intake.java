package org.firstinspires.ftc.teamcode.Components.Intake;

public class Intake {
    ActiveIntake activeIntake = new ActiveIntake();
    Spindexer spindexer = new Spindexer();
    Latch latch = new Latch();
    ColorDetection colorDetection =new ColorDetection();
    public void update(){
        latch.update();
        spindexer.update();
        activeIntake.update();
        colorDetection.update();
    }
}
