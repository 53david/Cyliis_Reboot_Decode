package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.opencv.core.Mat;

import java.lang.reflect.Array;

@Configurable
public class Storage {
    PIDController pid = new PIDController(Kp,0,Kd);
    ElapsedTime timer = new ElapsedTime();
    public double target = Math.PI / 6.0;//balls
    public double nrBalls = 0;
    public static double resetPos = Math.PI/6.0;
    public static double specialPos = Math.toRadians(330);
    public static double ballPos1 = Math.PI/6.0,ballPos2 = ballPos1 + Math.toRadians(130),ballPos3 = ballPos2 + Math.toRadians(120);
    public static int tValue =1000;
    public static double Kp = 0.7;
    public static double Kd = 0.04;
    public static double Ks = 0.08;

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
                    state = State.IDLE;
                    nrBalls=3;
                }
                break;
            case TRANSFER:
                target = Math.toRadians(specialPos);
                if(!IsStorageSpinning()) {
                    Latch.state = Latch.State.TRANSFER;
                }
                if (!IsStorageSpinning() && gm1.cross &&prevgm1.cross!= gm1.cross){
                    state = State.SHOOT;
                    timer.reset();
                }
                break;
            case SHOOT:
                pid.setPID(0,0,0);
                spin.setPower(-1);
                if (timer.seconds()>1){
                    state = State.RESET;
                    timer.reset();
                }
                break;
            case RESET:
                nrBalls = 0;
                pid.setPID(Kp,0,Kd);
                target = specialPos;
                Latch.state = Latch.State.IDLE;
                if (!IsStorageSpinning() && timer.seconds()>1) {
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
        stateUpdate();
        spinUpdate();
        if (state == State.TRANSFER && gm1.cross && prevgm1.cross != gm1.cross){
            state = State.SHOOT;
            timer.reset();
        }
    }
    public void tune(){
        spinUpdate();
        pid.setPID(Kp,0,Kd);
        telemetryM.addData("nrballs",nrBalls);
        telemetryM.addData("State",state);
        telemetryM.addData("Error",Math.toDegrees(Math.abs(target-FromVtoRads())));
        telemetryM.addData("Angle",Math.toDegrees(FromVtoRads()));
        telemetryM.addData("Target", target);
        telemetryM.addData("Is Storage Spinig?",IsStorageSpinning());
        telemetryM.addData("Is Ball in Storage?",ColorDetection.isBallInStorage());
        telemetryM.addData("Timer",timer.seconds());
        telemetryM.addData("Latch state",Latch.state);
        telemetryM.update();
    }
    public void spinUpdate(){
        if (state == State.SHOOT){
            pid.setPID(0,0,0);
        }
        else {
            spin.setPower(pid.calculate(FromVtoRads(), target) + Ks);
        }
        }
    public static double FromVtoRads(){
        return Math.abs(encoder.getVoltage() / encoder.getMaxVoltage()) *2.0 * Math.PI;
    }
    public void turn60(){
        target+= Math.PI/3;
        target = target % (350);
    }
    public void turn120(){
        target+= Math.PI * 2/3;
        target = target % (Math.toRadians(350));
    }
    public boolean IsStorageSpinning(){
        return Math.abs(target-FromVtoRads()) > Math.toRadians(12);
    }
    public static double getAngle(){
        return (encoder.getVoltage()/ encoder.getMaxVoltage()) *2.0 * Math.PI;
    }
    public void SetTarget(double angle){
        target = Math.toRadians(angle);
    }


}