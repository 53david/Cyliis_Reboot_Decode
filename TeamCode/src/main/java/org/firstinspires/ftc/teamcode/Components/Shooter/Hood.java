package org.firstinspires.ftc.teamcode.Components.Shooter;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.hood;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Math.ShooterCalculator;

@Configurable
public class Hood {
    public static double pos = 0.3;
    public static double IdlePos = 0.5;
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
                hood.setPosition(ShooterCalculator.hoodAngle(FlyWheel.getVelocity()));
                break;
        }
    }
    public Hood(){
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0.5);
    }
    public void update(){
        updateState();
        telemetryM.addData("Pos", ShooterCalculator.hoodAngle(FlyWheel.getVelocity()));
        telemetryM.update();
    }
    public void tune(){
        hood.setPosition(pos);
    }
}
