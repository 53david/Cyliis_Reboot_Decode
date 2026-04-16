package org.firstinspires.ftc.teamcode.Components.Shooter;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.pp;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.servo1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.servo2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@Configurable
public class Turret {
    public static double goalPositionX = 0, goalPositionY = 0;
    public double targetAngle = 0;
    public static double x = 0.1;
    public double maxAngle = normalizeRadians(360);
    Odo odo = new Odo();
    public enum AllienceState {
        RED,
        BLUE,
    }
    public static AllienceState state;
    public Turret() {
        servo1.setPwmRange(new PwmControl.PwmRange(500 , 2500));
        servo2.setPwmRange(new PwmControl.PwmRange(500 , 2500));
        servo1.setDirection(Servo.Direction.REVERSE);
        servo2.setDirection(Servo.Direction.REVERSE);

    }

    private void updateServosPosition() {

        targetAngle -= Odo.getCurrentPosition().h;
        targetAngle = normalizeRadians(targetAngle);
        targetAngle = targetAngle / maxAngle;

        double targetPosition = targetAngle;
        double left = targetPosition - 0.005;
        double right = targetPosition + 0.005;

        left = Math.max(0.0007, left);
        left = Math.min(1 - 0.0007, left);
        right = Math.max(0.0007, right);
        right = Math.min(1 - 0.0007, right);

        telemetryM.addData("left",left);
        telemetryM.addData("right",right);
        telemetryM.update();

    }
    public void updateAngle() {

        double dx = goalPositionX - Odo.trueX();
        double dy = goalPositionY - Odo.trueY();
        targetAngle = Math.atan2(((goalPositionY)-dy),((goalPositionX)-dx));
        telemetryM.addData("Angle",targetAngle);
        telemetryM.update();

    }

    public void update() {
        servo1.setPosition(x-0.005);
        servo2.setPosition(x+0.005);
    }
    public void AllienceUpdate(){
        switch (state){
            case BLUE:
                goalPositionX = 840; goalPositionY = 0;
                break;
            case RED:
                goalPositionX = -840; goalPositionY = 20;
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
        odo.update();
        telemetryM.update();
    }
}