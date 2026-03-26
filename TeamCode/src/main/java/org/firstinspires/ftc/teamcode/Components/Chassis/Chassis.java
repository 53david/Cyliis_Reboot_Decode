package org.firstinspires.ftc.teamcode.Components.Chassis;

public class Chassis {
    public Odo odo =new Odo();
    public Drive drive =new Drive();
    public void update(){
        odo.update();
        drive.update();
    }
}
