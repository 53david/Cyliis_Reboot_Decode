package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.Shooter.Hood;


@Configurable
public class Storage {
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDController special = new PIDController(P,0,D);
    ElapsedTime timer = new ElapsedTime();
    public static boolean isTransferReady = false;
    public static double target = Math.PI/4.0;
    public static double nrBalls =  0;
    public static double resetPos = Math.PI/4.0;
    public static double specialPos = Math.toRadians(317);
    public static double ballPos1 = Math.PI/4.,ballPos2 = ballPos1 + Math.PI *2/3,ballPos3 = ballPos2 + Math.PI*2/3;
    public static double Kp = 0.9;
    public static double Kd = 0.03;
    public static double P = 1.8;
    public static double D = 0.06;
    public static double Ks = 0;
    public enum State{
        TRANSFER,
        IDLE,
        SHOOT,
        RESET,
    }
    public static State state;

    public Storage(){
        timer.startTime();
        state = State.IDLE;
        isTransferReady = false;
    }
    public void stateUpdate(){

        switch (state){
            case IDLE:
                Hood.state = Hood.State.IDLE;
                if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==0){
                    nrBalls = 1;
                    target = ballPos2;
                    isTransferReady= false;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==1){
                    nrBalls = 2;
                    target = ballPos3;
                    isTransferReady = false;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==2){
                    nrBalls = 3;
                    target = specialPos;
                    state = State.TRANSFER;
                    isTransferReady = true;
                }
                break;
            case TRANSFER:
                isTransferReady = false;
                if(!IsStorageSpinning() && timer.seconds()>0.25){
                    Latch.state = Latch.State.TRANSFER;
                }
                if (!IsStorageSpinning() && gm1.cross){
                    state = State.SHOOT;
                    Hood.state = Hood.State.SHOOT;
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
                target = resetPos;
                Hood.state = Hood.State.IDLE;
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
        spinUpdate();
        stateUpdate();
        if (state == State.TRANSFER && gm1.cross && prevgm1.cross != gm1.cross){
            state = State.SHOOT;
            timer.reset();
        }
        if (gm1.circle && prevgm1.circle!= gm1.circle && nrBalls>=1){
            target = specialPos;
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
                spin.setPower(pid.calculate(FromVtoRads(), target) + Ks * Math.signum(FromVtoRads()-target));
            }
            else {
                spin.setPower(special.calculate(FromVtoRads(), target) + Ks * Math.signum(FromVtoRads()-target));
            }
        }
        }
    public static double FromVtoRads(){
        return Math.abs(encoder.getVoltage() / 3.3) *2.0 * Math.PI;
    }
    public boolean IsStorageSpinning(){
        return Math.abs(target-FromVtoRads()) > Math.toRadians(10);
    }

}