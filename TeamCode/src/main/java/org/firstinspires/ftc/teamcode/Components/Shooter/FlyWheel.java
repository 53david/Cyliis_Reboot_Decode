package org.firstinspires.ftc.teamcode.Components.Shooter;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.Voltage;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.frontLeft;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Kd;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Ki;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Kp;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Ks;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Kv;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Math.ShooterCalculator;

@Configurable
public class FlyWheel {
    PIDController controller = new PIDController(Kp,Ki,Kd);
    public enum State{
        IDLE,
        SHOOT,
    }
    public static State state = State.SHOOT;
    public static double vel = 1400;
    public static double rpm = 0;
    public FlyWheel(){
        shoot1.setDirection(DcMotorSimple.Direction.REVERSE);
        shoot2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void updateState(){
        switch (state){
            case IDLE :
                vel = 920;
                break;
            case SHOOT:
                vel = ShooterCalculator.fwVel(Odo.distance());
                break;
        }
    }
    public void update(){
        controller.setPID(Kp,0,Kd);
        updateState();
        if (Math.abs(getVelocity()-vel)<=20){
            vel = getVelocity();
        }
        rpm = controller.calculate(getVelocity(),vel);
        rpm += Kv * vel + Math.signum(vel-getVelocity())*Ks;
        rpm *= Voltage;
        shoot1.setPower(rpm);
        shoot2.setPower(rpm);

    }
    public static double getVelocity(){
        return Math.abs(frontLeft.getVelocity());
    }
    public void tune(){
        controller.setPID(Kp,0,Kd);
        double error = Math.abs(vel-getVelocity());
        rpm = controller.calculate(getVelocity(),vel);
        rpm += Kv * vel + Math.signum(vel-getVelocity())*Ks;
        rpm *= Voltage;
        shoot1.setPower(rpm);
        shoot2.setPower(rpm);
        telemetryM.addData("Velocity",getVelocity());
        telemetryM.addData("Error",error);
        telemetryM.update();
    }
}