package org.firstinspires.ftc.teamcode.Wrappers;

import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
public class Robot {
    Intake intake;
    Shooter shooter;
    public Robot(){
        intake = new Intake();
        shooter = new Shooter();
    }
    public void update(){
        intake.update();
        shooter.update();
    }
}
