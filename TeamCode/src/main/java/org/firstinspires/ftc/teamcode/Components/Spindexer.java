package org.firstinspires.ftc.teamcode.Components;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.Spin;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.Wrappers.PIDController;

@Configurable
public class Spindexer {
    double target = 0;
    public static double Kp = 0;
    public static double Kd = 0;
    public enum State{
        BALL,
        TURN,
        IDLE,
    }
    State state;
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDCoefficients coef = new PIDCoefficients(Kp,0,Kd);

    public Spindexer(){
        Spin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        state = State.IDLE;
    }
    public void update(){
        spinUpdate();
        switch (state){
            case IDLE:

                break;

            case TURN:


                break;

        }
    }
    public void test(){
        pid.setPidCoefficients(coef);
    }
    public double FromTicksToDegrees(){
        return Spin.getCurrentPosition() / 8192.0 * Math.PI * 2.0;
    }
    public void spinUpdate(){
        Spin.setPower(pid.calculatePower(FromTicksToDegrees()));

    }
    public void turn60(){
        target+= Math.PI /3;
        pid.setTargetPosition(target);
    }
    public void turn120(){
        target+= Math.PI * 2/3;
        pid.setTargetPosition(target);
    }
}