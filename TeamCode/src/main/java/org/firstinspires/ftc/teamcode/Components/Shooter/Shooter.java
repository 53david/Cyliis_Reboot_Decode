package org.firstinspires.ftc.teamcode.Components.Shooter;

public class Shooter {
    FlyWheel flyWheel;
    Turret turret;
    public Shooter(){
        flyWheel =new FlyWheel();
        turret =new Turret();
    }
    public void update(){
        flyWheel.update();
        turret.update();
    }
}
