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
    public static double specialPos = Math.toRadians(310);
    public static double ballPos1 = Math.PI/6.0,ballPos2 = ballPos1 + Math.PI *2/3,ballPos3 = ballPos2 + Math.PI*2/3;
    public static double Kp = 0.8;
    public static double Kd = 0.04;
    public static double P = 0;
    public static double D = 0;
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
                    state = State.BALL1;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==1){
                    state = State.BALL2;
                }
                else if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls==2){
                    state = State.BALL3;
                }
                if (nrBalls==3 && !IsStorageSpinning()){
                    timer.reset();
                    state = State.TRANSFER;
                }
                break;
            case BALL1:
                target = ballPos2;
                if (!IsStorageSpinning()) {
                    state = State.IDLE;
                    nrBalls=1;
                }
                break;
            case BALL2:
                target = ballPos3;
                if (!IsStorageSpinning()) {
                    state = State.IDLE;
                    nrBalls=2;
                }
                break;

            case BALL3:
                if (!IsStorageSpinning()) {
                    state = State.TRANSFER;
                    nrBalls=3;
                }
                break;
            case TRANSFER:
                target = specialPos;
                if(!IsStorageSpinning())    {
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
                if (!IsStorageSpinning() && timer.seconds()>0.6) {
                    state = State.IDLE;
                    target = resetPos;
                }
                else if (!IsStorageSpinning() && timer.seconds()>0.2){
                    Latch.state = Latch.State.IDLE;
                }
                break;

        }
    }
    public void update(){
        getError();
        stateUpdate();
        spinUpdate();
        if (state == State.TRANSFER && gm1.cross && prevgm1.cross != gm1.cross){
            state = State.SHOOT;
            timer.reset();
        }
        if (gm1.circle && prevgm1.circle!= gm1.circle && nrBalls>=1){
            state = State.TRANSFER;
        }
    }
    public void tune(){

        special.setPID(P,0,D);
        pid.setPID(Kp,0,Kd);
        spinUpdate();

        telemetryM.addData("Error",Math.toDegrees(error));
        telemetryM.addData("Current position",Math.toDegrees(FromVtoRads()));
        telemetryM.addData("Target",Math.toDegrees(target));
        telemetryM.addData("Is ball in Storage",ColorDetection.isBallInStorage());
        telemetryM.addData("Is Storage Spinning",IsStorageSpinning());
        telemetryM.update();

    }
    public void spinUpdate(){
        if (state == State.SHOOT){
            pid.setPID(0,0,0);
        }
        else {
            if (error>Math.toRadians(10)) {
                spin.setPower(pid.calculate(FromVtoRads(),target) + Ks);
            }
            else {
                spin.setPower(special.calculate(FromVtoRads(),target) + Ks);
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
        return Math.abs(target-FromVtoRads()) > Math.toRadians(5);
    }
    public void setTargetAngle(double angle){
        target = Math.toRadians(angle);
    }

}