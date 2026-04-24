package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Configurable
public class ActiveIntake {
    public enum State{
        IDLE,
        INTAKE,
        REVERSE,
    }
    public static State state;
    public ActiveIntake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        state = State.IDLE;

    }
    public void stateUpdate() {
        switch (state){
            case INTAKE:
                intakeMotor.setPower(1);
                break;
            case IDLE:
                intakeMotor.setPower(0);
                break;
            case REVERSE:
                intakeMotor.setPower(-1);
                break;
        }
    }
    public void update(){
        if (gm1.left_bumper){
            intakeMotor.setPower(-1);
        }
        else if (gm1.right_bumper){
            intakeMotor.setPower(1);
        }
        else {
            intakeMotor.setPower(0);
        }
    }
}