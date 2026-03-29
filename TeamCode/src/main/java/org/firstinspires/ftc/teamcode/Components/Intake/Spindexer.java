package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.OpModes.Autonomous.BlueClose.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.OpModes.Autonomous.BlueClose.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Wrappers.PIDController;

@Configurable
public class Spindexer {
    ElapsedTime timer = new ElapsedTime();
    double target = 0;
    public static double nrBalls = 0;
    boolean isReady = false;
    public static int tValue =1000;
    public static double Kp = 0;
    public static double Kd = 0;

    public enum State{
        BALL,
        TRANSFER,
        IDLE,
        SHOOT,
    }
    public static State state;
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDCoefficients coef = new PIDCoefficients(Kp,0,Kd);

    public Spindexer(){
        spin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        state = State.IDLE;
    }
    public void update(){
        telemetryM.addData("Artifacts in storage",nrBalls);
        telemetryM.update();
        spinUpdate();
        switch (state){
            case IDLE:
                Latch.state = Latch.State.IDLE;
                if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls<3){
                    state = State.BALL;
                }
                if (ColorDetection.isBallInStorage() && !IsStorageSpinning() && nrBalls == 3){
                    state = State.TRANSFER;
                    gm1.rumble(tValue);
                }
                break;
            case BALL:
                turn120();
                nrBalls++;
                state = State.IDLE;
                break;
            case TRANSFER:
                Latch.state = Latch.State.TRANSFER;
                if (!isReady) {
                       turn60();
                    isReady = true;
                }
                if (!isAutonomousActive && gm1.cross && prevgm1.cross!=gm1.cross){
                    state = State.SHOOT;
                    nrBalls = 0;
                    timer.startTime();
                    timer.reset();
                }
                break;
            case SHOOT:
                turn120();
                if (timer.seconds()>1){state = State.IDLE; spin.setPower(0); timer.reset(); isReady=false; turn60();}
                break;
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