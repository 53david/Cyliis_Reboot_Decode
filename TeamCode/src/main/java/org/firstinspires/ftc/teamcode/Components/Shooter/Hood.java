package org.firstinspires.ftc.teamcode.Components.Shooter;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.hood;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants;

public class Hood {
    public static double delta = 200;
    public Hood(){
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0.5);
    }
    public void update(){
        hood.setPosition(ShooterConstants.hoodAngle(Odo.distance()));
    }
}
