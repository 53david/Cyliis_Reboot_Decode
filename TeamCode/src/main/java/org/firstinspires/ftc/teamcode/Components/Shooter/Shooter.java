package org.firstinspires.ftc.teamcode.Components.Shooter;

public class Shooter {
    FlyWheel flyWheel =new FlyWheel();
    Turret turret =new Turret();
    Vision vision = new Vision();
    public void update(){
        flyWheel.update();
        turret.update();
        vision.update();
    }
}
