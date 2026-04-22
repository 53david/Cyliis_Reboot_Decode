package org.firstinspires.ftc.teamcode.Components.Shooter;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.servo1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.servo2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@Configurable
public class Turret {
    public static double goalPositionX = 0, goalPositionY = 0;
    public double targetAngle = 0;
    public double targetPosition = 0.5;
    public double maxAngle = Math.PI*2;
    Odo odo = new Odo();
    public enum AllianceState {
        RED,
        BLUE,
    }
    public enum State{
        AUTO,
        MANUAL,
    }
    public static AllianceState allienceState;
    public State state;
    public Turret() {

        state = State.MANUAL;
        servo1.setPwmRange(new PwmControl.PwmRange(500 , 2500));
        servo2.setPwmRange(new PwmControl.PwmRange(500 , 2500));

        servo1.setDirection(Servo.Direction.REVERSE);
        servo2.setDirection(Servo.Direction.REVERSE);

    }

    private void updateServosPosition() {

        targetAngle -= Odo.getHeading();
        targetAngle = normalizeRadians(targetAngle);
        targetAngle = targetAngle / maxAngle;

        switch (state) {
            case AUTO :
                targetPosition = targetAngle;
                if (gm1.dpad_right && prevgm1.dpad_right != gm1.dpad_right){
                    state = State.MANUAL;
                }
                break;
            case MANUAL:
                targetPosition = 0.5;
                if (gm1.dpad_right && prevgm1.dpad_right != gm1.dpad_right){
                    state = State.AUTO;
                }
                break;
        }
        targetPosition = Math.max(0.007, targetPosition);
        targetPosition = Math.min(1 - 0.007, targetPosition);

        servo1.setPosition(targetPosition);
        servo2.setPosition(targetPosition);
    }
    public void updateAngle() {

        double dx = goalPositionX - Odo.getX();
        double dy = goalPositionY - Odo.getY();
        targetAngle = Math.atan2((dy),(dx));

    }

    public void update() {
        AllianceUpdate();
        updateAngle();
        updateServosPosition();
    }
    public void AllianceUpdate(){
        switch (allienceState){
            case BLUE:
                goalPositionX = 0; goalPositionY = 840;
                break;
            case RED:
                goalPositionX = -20 ; goalPositionY = -840;
                break;
        }
    }
    public double normalizeRadians(double angle){
        angle %= (2.0 * Math.PI);
        if (angle < 0) angle += (2.0 * Math.PI);
        return angle;

    }
    public void test(){

        telemetryM.addData("Angle",targetAngle);
        telemetryM.addData("X",Odo.getX());
        telemetryM.addData("y",Odo.getY());
        telemetryM.addData("Heading",Odo.getHeading());
        telemetryM.addData("Goal X",goalPositionX);
        telemetryM.addData("Goal Y",goalPositionY);
        odo.update();
        telemetryM.update();
        AllianceUpdate();
        updateAngle();
        updateServosPosition();

    }
}