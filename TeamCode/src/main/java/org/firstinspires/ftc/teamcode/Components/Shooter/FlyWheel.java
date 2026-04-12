package org.firstinspires.ftc.teamcode.Components.Shooter;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.Voltage;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Ka;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Kd;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Ki;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Kp;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Ks;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Kv;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants;

@Configurable
public class FlyWheel {
    PIDController controller = new PIDController(Kp,Ki,Kd);
    public enum State{
        IDLE,
        SHOOT,
    };
    public static State state = State.IDLE;
    public static double rpm = 0;
    public FlyWheel(){
        shoot1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shoot2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void updateState(){
        switch (state){
            case IDLE :
                rpm = 600;
                break;
            case SHOOT:
                break;
        }
    }
    public void update(){
        updateState();
        double error = rpm-shoot2.getVelocity();
        rpm = controller.calculate(shoot2.getVelocity(),ShooterConstants.vel);
        rpm += Kv * rpm + Ks;
        rpm *= Voltage;
        if (error>600) {
            rpm += Ka * (error);
        }
        shoot1.setPower(rpm);
        shoot2.setPower(rpm);

    }
    public static double getVelocity(){
        return shoot2.getVelocity();
    }
    public void tune(){
        double error = rpm-shoot2.getVelocity();
        rpm = controller.calculate(shoot2.getVelocity(),ShooterConstants.vel);
        rpm += Kv * rpm + Ks;
        rpm *= Voltage;
        if (error>600) {
            rpm += Ka * (error);
        }
        shoot1.setPower(rpm);
        shoot2.setPower(rpm);
        telemetryM.addData("Velocity",getVelocity());
        telemetryM.addData("Error",error);
        telemetryM.update();
    }
}