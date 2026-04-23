package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;

import com.qualcomm.robotcore.util.ElapsedTime;


@Configurable
public class Storage {
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDController special = new PIDController(P,0,D);
    ElapsedTime timer = new ElapsedTime();
    public static double target = Math.PI/6.0;
    public static double nrBalls =  0;
    public static double resetPos = Math.PI/6.0;
    public static double specialPos = Math.toRadians(314);
    public static double ballPos1 = Math.PI/6.0,ballPos2 = ballPos1 + Math.PI *2/3,ballPos3 = ballPos2 + Math.PI*2/3;
    public static double Kp = 0.7;
    public static double Kd = 0.03;
    public static double P = 1.6;
    public static double D = 0.07;
    public static double Ks = 0.09;
    public double error = 0;
    public enum State{
        BALL1,
        BALL2,
        BALL3,
        TRANSFER,
        IDLE,
        SHOOT,
        RESET,
    }
    public static State state;

    public Storage(){
        timer.startTime();
        state = State.IDLE;
    }
    public void stateUpdate(){

        switch (state){
            case IDLE:
                if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==0){
                    nrBalls = 1;
                    target = ballPos2;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==1){
                    nrBalls = 2;
                    target = ballPos3;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==2){
                    nrBalls = 3;
                    target = specialPos;
                }
                break;

            case TRANSFER:
                if(!IsStorageSpinning() && timer.seconds()>0.25)    {
                    Latch.state = Latch.State.TRANSFER;
                }
                if (!IsStorageSpinning() && gm1.cross){
                    state = State.SHOOT;
                    timer.reset();
                }
                break;

            case SHOOT:
                pid.setPID(0,0,0);
                spin.setPower(-1);
                if (timer.seconds()>0.6){
                    state = State.RESET;
                    timer.reset();
                }
                break;

            case RESET:
                nrBalls = 0;
                pid.setPID(Kp,0,Kd);
                target = specialPos;
                if (!IsStorageSpinning() && Latch.state == Latch.State.IDLE) {
                    state = State.IDLE;
                    target = resetPos;
                }
                else if (!IsStorageSpinning()){
                    Latch.state = Latch.State.IDLE;
                }
                break;

        }
    }
    public void update(){
        getError();
        spinUpdate();
        stateUpdate();
        if (state == State.TRANSFER && gm1.cross && prevgm1.cross != gm1.cross){
            state = State.SHOOT;
            timer.reset();
        }
        if (gm1.circle && prevgm1.circle!= gm1.circle && nrBalls>=1){
            state = State.TRANSFER;
        }
        pid.setPID(Kp,0,Kd);
        special.setPID(P,0,D);
    }
    public void spinUpdate(){
        if (state == State.SHOOT){
            pid.setPID(0,0,0);
        }
        else {
            if (Math.toDegrees(Math.abs(FromVtoRads()-target)) >= 10) {
                spin.setPower(pid.calculate(FromVtoRads(), target) + Ks);
            }
            else {
                spin.setPower(special.calculate(FromVtoRads(), target) + Ks);
            }
        }
        }
    public static double FromVtoRads(){
        return Math.abs(encoder.getVoltage() / encoder.getMaxVoltage()) *2.0 * Math.PI;
    }
    public void getError(){
        error = Math.abs(target-FromVtoRads());
    }
    public boolean IsStorageSpinning(){
        return Math.abs(target-FromVtoRads()) > Math.toRadians(20);
    }
    public void setTargetAngle(double angle){
        target = Math.toRadians(angle);
    }

}