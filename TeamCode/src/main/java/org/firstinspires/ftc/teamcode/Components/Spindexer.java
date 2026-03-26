package org.firstinspires.ftc.teamcode.Components;
import static org.firstinspires.ftc.teamcode.OpModes.Teleop.gm1;
import static org.firstinspires.ftc.teamcode.OpModes.Teleop.prevgm1;
import static org.firstinspires.ftc.teamcode.OpModes.Teleop.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Wrappers.PIDController;

@Configurable
public class Spindexer {
    ElapsedTime timer = new ElapsedTime();
    double target = 0;
    double angle = 0;
    double nrBalls = 0;
    boolean isReady = false;
    public static double Kp = 0;
    public static double Kd = 0;
    public static double rpm = 0;
    public enum State{
        BALL,
        TRANSFER,
        IDLE,
        SHOOT,
    }
    State state;
    PIDController pid = new PIDController(Kp,0,Kd);
    PIDCoefficients coef = new PIDCoefficients(Kp,0,Kd);

    public Spindexer(){
        spin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        state = State.IDLE;
    }
    public void update(){
        spinUpdate();
        switch (state){
            case IDLE:
                telemetryM.addLine("IDLE");
                break;
            case BALL:
                turn120();
                nrBalls++;
                state = State.IDLE;
                break;
            case TRANSFER:
                if (!isReady) {
                       turn60();
                    isReady = true;
                }
                if (gm1.cross && prevgm1.cross!=gm1.cross){
                    state = State.SHOOT;
                    nrBalls = 0;
                    timer.startTime();
                    timer.reset();
                }
                break;
            case SHOOT:
                spin.setPower(pid.calculatePower(rpm));
                if (timer.seconds()>1){state = State.IDLE; spin.setPower(0);}
                break;
        }
    }
    public void test(){
        pid.setPidCoefficients(coef);
    }
    public void spinUpdate(){
        spin.setPower(pid.calculatePower(FromVtoRads()));
    }
    public double FromVtoRads(){
            return (encoder.getVoltage() / 3.3) * 2.0 * Math.PI;
    }
    public void turn60(){
        target+= Math.PI/3;
        pid.setTargetPosition(target);
    }
    public void turn120(){
        target+= Math.PI * 2/3;
        pid.setTargetPosition(target);
    }
}