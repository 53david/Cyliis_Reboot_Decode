package org.firstinspires.ftc.teamcode.Components.Shooter;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.hood;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants;

@Configurable
public class Hood {
    public static double pos = 0.3;
    public static double IdlePos = 0.5;
    public static double ShootPos = 0.3;
    public enum State{
        IDLE,
        SHOOT,
    }
    public static State state = State.IDLE;
    public void updateState(){
        switch (state){
            case IDLE :
                hood.setPosition(IdlePos);
                break;
            case SHOOT:
                hood.setPosition(ShootPos);
                break;
        }
    }
    public Hood(){
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0.5);
    }
    public void update(){
        updateState();
        telemetryM.addData("Pos",ShooterConstants.hoodAngle(Odo.distance()));
        telemetryM.update();
    }
    public void tune(){
        hood.setPosition(pos);
    }
}
