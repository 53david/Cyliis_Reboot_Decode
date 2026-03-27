package org.firstinspires.ftc.teamcode.Components.Chassis;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;

public class Chassis {
    public Drive drive =new Drive();
    public void update(){
        drive.update();
    }
}
