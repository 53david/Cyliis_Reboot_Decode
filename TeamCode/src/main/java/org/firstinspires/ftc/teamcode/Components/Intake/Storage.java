package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Wrappers.PIDController;

import java.lang.reflect.Array;

@Configurable
public class Storage {
    ElapsedTime timer = new ElapsedTime();
    public static double target = 0;
    public double nrBalls = 0;
    public static double resetPos = -0.76;
    public static double specialPos = Math.PI - 0.26;
    public static double ballPos1 = Math.PI/3,ballPos2 = ballPos1 + Math.PI*2/3,ballPos3 = ballPos2 + Math.PI*2/3;
    public static int tValue =1000;
    public static double Kp = 0.48;
    public static double Kd = 0.03;
    public static double Ks = 0.08;
    PIDCoefficients coef1 = new PIDCoefficients(Kp,0,Kd);
    PIDCoefficients coef2 = new PIDCoefficients(0,0,0);

    public enum State{
        BALL1,
        BALL2,
        BALL3,
        TRANSFER,
        IDLE,
        SHOOT,
        RESET,
    };
    public static State state;
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDCoefficients coef = new PIDCoefficients(Kp,0,Kd);

    public Storage(){
        state = State.IDLE;
    }
    public void stateUpdate(){

        switch (state){
            case IDLE:
                if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==0){
                    state = State.BALL1;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==1){
                    state = State.BALL2;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==2){
                    state = State.BALL3;
                }
                if (nrBalls==3 && !IsStorageSpinning()){
                    state = State.TRANSFER;
                }
                break;
            case BALL1:
                pid.setTargetPosition(ballPos1);
                if (!IsStorageSpinning()) {
                    state = State.IDLE;
                    nrBalls=1;
                }
                break;
            case BALL2:
                pid.setTargetPosition(ballPos2);
                if (!IsStorageSpinning()) {
                    state = State.IDLE;
                    nrBalls=2;
                }
                break;

            case BALL3:
                pid.setTargetPosition(ballPos3);
                if (!IsStorageSpinning()) {
                    state = State.IDLE;
                    nrBalls=3;
                }
                break;
            case TRANSFER:
                spin.setPower(pid.calculatePower(specialPos));
                break;
            case SHOOT:
                pid.setPidCoefficients(coef2);
                spin.setPower(1);
                if (timer.seconds()>1.5){
                    state = State.RESET;
                    pid.setPidCoefficients(coef1);
                }
                break;
            case RESET:
                nrBalls = 0;
                spin.setPower(pid.calculatePower(resetPos));
                if (!IsStorageSpinning()) {
                    state = State.IDLE;
                }
                break;

        }
        telemetryM.addData("nrballs",nrBalls);
        telemetryM.update();
    }
    public void update(){
        stateUpdate();
        spinUpdate();
        if (state == State.TRANSFER && gm1.cross && prevgm1.cross != gm1.cross){
            state = State.SHOOT;
            timer.reset();
        }
    }
    public void tune(){
        spinUpdate();
        coef = new PIDCoefficients(Kp,0,Kd);
        pid.setPidCoefficients(coef);
    }
    public void spinUpdate(){
        spin.setPower(pid.calculatePower(FromVtoRads())+Ks) ;
    }
    public static double FromVtoRads(){
        return (encoder.getVoltage() / encoder.getMaxVoltage()) *2.0 * Math.PI;
    }
    public void turn60(){
        target+= Math.PI /3;
        target = target % (Math.PI*2);
        pid.setTargetPosition(target);
    }
    public void turn120(){
        target+= Math.PI * 2/3;
        target = target % (Math.toRadians(350));
        pid.setTargetPosition(target);
    }
    public static boolean IsStorageSpinning(){
        return Math.abs(target-FromVtoRads()) > Math.toRadians(15);
    }
    public static double getAngle(){
        return (encoder.getVoltage()/ encoder.getMaxVoltage()) *2.0 * Math.PI;
    }
    public void SetTarget(double angle){
        pid.setTargetPosition(Math.toRadians(angle));
    }


}