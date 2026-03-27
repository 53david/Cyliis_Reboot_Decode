package org.firstinspires.ftc.teamcode.Components.Shooter;

import org.firstinspires.ftc.teamcode.Components.Intake.Intake;

public class Shooter {
    FlyWheel flyWheel;
    Turret turret;
    Intake intake;
    public Shooter(){
        flyWheel =new FlyWheel();
        turret =new Turret();
    }
    public void update(){
        flyWheel.update();
        turret.update();
    }
}
