package org.firstinspires.ftc.teamcode.Components.Shooter;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.Voltage;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Kd;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Ki;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Kp;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Ks;
import static org.firstinspires.ftc.teamcode.Wrappers.ShooterConstants.Kv;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;

@Configurable
public class FlyWheel {
    PIDController controller = new PIDController(Kp,Ki,Kd);
    double vel1 = 0;
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
                rpm = 800;
                break;
            case SHOOT:
                rpm = 1200;
                break;
        }
    }
    public void update(){
        updateState();
        vel1 = controller.calculate(shoot2.getVelocity(),rpm);
        vel1 += Kv * rpm + Ks;
        vel1 *= Voltage;
        shoot1.setPower(vel1);
        shoot2.setPower(vel1);
        telemetryM.addData("Velocity",Math.abs(shoot2.getVelocity()));
        telemetryM.update();

    }
    public void tune(){
        controller = new PIDController(Kp,Ki,Kd);
        vel1 = controller.calculate(shoot2.getVelocity(),rpm);
        vel1 += Kv * rpm + Ks;
        vel1 *= Voltage;
        shoot1.setPower(vel1);
        shoot2.setPower(vel1);
        telemetryM.addData("Velocity",Math.abs(shoot2.getVelocity()));
    }
}