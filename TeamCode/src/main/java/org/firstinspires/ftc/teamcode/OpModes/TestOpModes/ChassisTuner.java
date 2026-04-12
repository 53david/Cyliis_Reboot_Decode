package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;
@Configurable
@TeleOp
public class ChassisTuner extends LinearOpMode {
    Pose2D start = new Pose2D(0,0,Math.PI/2);
    Pose2D finish = new Pose2D(1200,0,Math.PI/2);
    Chassis chassis;
    Odo odo;
    public static boolean isReady;
    public enum State{
        IDLE,
        FORWARD,
        BACKWARD,
    };
    State state = State.IDLE;
    public void updateState(){
        switch (state){
            case IDLE:
                chassis.setTargetPosition(start);
                if (isReady)
                    state = State.FORWARD;
                break;
            case FORWARD:
                chassis.setTargetPosition(finish);
                if (chassis.inPosition(10,10,0.2))
                    state = State.BACKWARD;
                break;
            case BACKWARD:
                chassis.setTargetPosition(start);
                if (chassis.inPosition(10,10,0.2)) {
                    state = State.IDLE;
                     isReady = false;
                }
                break;
        }
    }
    public void runOpMode(){
        chassis = new Chassis(Chassis.State.PID);
        odo = new Odo();
        waitForStart();
        while (opModeIsActive()){
            updateState();
            chassis.update();
            odo.update();
        }
    }

}
