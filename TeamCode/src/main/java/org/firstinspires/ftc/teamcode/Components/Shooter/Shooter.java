package org.firstinspires.ftc.teamcode.Components.Shooter;

import org.firstinspires.ftc.teamcode.Components.Intake.Intake;

public class Shooter {
    FlyWheel flyWheel;
    Turret turret;
    Hood hood;
    public Shooter(){
        flyWheel =new FlyWheel();
        turret =new Turret();
        hood = new Hood();
    }
    public void update(){
        hood.update();
        flyWheel.update();
        turret.update();
    }
}
