package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Wrappers.PIDController;

@Configurable
public class Spindexer {
    ElapsedTime timer = new ElapsedTime();
    double target = 0;
    double timerTreshold = 1.5;
    public static double nrBalls = 0;
    public static double resetPos = Math.PI*2/3;
    public static double specialPos = Math.PI - 0.26;
    public double power = 400;
    public static int tValue =1000;
    public static double Kp = 0;
    public static double Kd = 0;

    public enum State{
        BALL,
        TRANSFER,
        IDLE,
        SHOOT,
        RESET,
    };
    public static State state;
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDCoefficients coef = new PIDCoefficients(Kp,0,Kd);

    public Spindexer(){
        spin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        state = State.IDLE;
    }
    public void stateUpdate(){

        switch (state){
            case IDLE:
                if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls<3){
                    state = State.BALL;
                }
                break;
            case BALL:
                turn120();
                nrBalls++;
                state = State.IDLE;
                break;
            case TRANSFER:
                spin.setPower(pid.calculatePower(specialPos));
                timer.startTime();
                timer.reset();
                break;
            case SHOOT:
                turn120();
                if (timer.seconds()>timerTreshold){
                    state = State.RESET;
                }
            case RESET:
                nrBalls = 0;
                spin.setPower(pid.calculatePower(resetPos));
                state = State.IDLE;
                break;
        }
    }
    public void update(){
        stateUpdate();
        spinUpdate();
        if (state == State.TRANSFER && gm1.cross && prevgm1.cross != gm1.cross){
            state = State.SHOOT;
        }
        if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls == 3){
            state = State.TRANSFER;
            gm1.rumble(tValue);
        }
    }
    public void tune(){coef = new PIDCoefficients(Kp,0,Kd);
        pid.setPidCoefficients(coef);
    }
    public void spinUpdate(){
        spin.setPower(pid.calculatePower(FromVtoRads()));
    }
    public double FromVtoRads(){
            return (encoder.getVoltage() / 3.3) * 2.0 * Math.PI;
    }
    public void turn60(){
        target+= Math.PI /3 % (Math.PI*2);
        pid.setTargetPosition(target);
    }
    public void turn120(){
        target+= Math.PI * 2/3 % (Math.PI*2);
        pid.setTargetPosition(target);
    }
    public boolean IsStorageSpinning(){
        return Math.abs(target-FromVtoRads()) < Math.toRadians(5) && spin.getVelocity() < 20;
    }

}