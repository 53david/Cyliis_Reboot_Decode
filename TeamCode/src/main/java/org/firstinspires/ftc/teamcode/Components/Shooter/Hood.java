package org.firstinspires.ftc.teamcode.Components.Shooter;
import static org.firstinspires.ftc.teamcode.OpModes.TeleopBlue.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.hood;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants;

public class Hood {
    public static double pos = 0.5;
    public Hood(){
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0.5);
    }
    public void update(){
        telemetryM.addData("Pos",ShooterConstants.hoodAngle(Odo.distance()));
        telemetryM.update();
    }
    public void tuning(){
        hood.setPosition(pos);
    }
}
