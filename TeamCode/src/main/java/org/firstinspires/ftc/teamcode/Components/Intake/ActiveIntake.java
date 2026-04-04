package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;


@Configurable
public class ActiveIntake {
    public static double maxRPM = 1;
    public static double maxPower = 1;
    public static double zeroPower =0;
    public static double reversePower = -1;
    public enum State{
        IDLE,
        INTAKE,
        REVERSE,
    };
    public static State state;
    public ActiveIntake() {

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorConfigurationType unlocked = intakeMotor.getMotorType();
        unlocked.setAchieveableMaxRPMFraction(maxRPM);
        intakeMotor.setMotorType(unlocked);
        state = State.IDLE;
    }
    public void stateUpdate() {
        switch (state){
            case INTAKE:
                intakeMotor.setPower(maxPower);
                break;
            case IDLE:
                intakeMotor.setPower(zeroPower);
                break;
            case REVERSE:
                intakeMotor.setPower(reversePower);
                break;
        }
    }
    public void update(){
        stateUpdate();
        if (!isAutonomousActive) {
            if (gm1.left_bumper && gm1.left_bumper == prevgm1.left_bumper) {
                state = State.REVERSE;
            } else if (gm1.right_bumper && gm1.right_bumper == prevgm1.right_bumper) {
                state = State.INTAKE;
            }
            else {
                state = State.IDLE;
            }
        }
    }
}