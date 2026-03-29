package org.firstinspires.ftc.teamcode.Components.Chassis;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.Voltage;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.leftBack;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.leftFront;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.rightBack;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.rightFront;


import com.bylazar.configurables.annotations.Configurable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@Configurable
public class Drive{
    public static double target = 0;
    public static double maxRPM = 1;
    public static double multiplier = 0.01;
    public Drive() {

        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightBack.setDirection(DcMotorEx.Direction.REVERSE);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorConfigurationType unlock= leftFront.getMotorType();
        unlock.setAchieveableMaxRPMFraction(maxRPM);
        leftFront.setMotorType(unlock);
        rightFront.setMotorType(unlock);
        leftBack.setMotorType(unlock);
        rightBack.setMotorType(unlock);

    }
    public void update(){
        double y = -gm1.left_stick_y;
        double x = gm1.left_stick_x;
        double rx = gm1.left_trigger - gm1.right_trigger;
        double yaw = Odo.getCurrentPosition().h;

        if (gm1.triangle){
            target = 45;
            double error = target-yaw;
            if (error>180){
                error-=360;
            }
            else if (error<-180){
                error+=360;
            }
            rx =  multiplier * error;
            rx = Math.min(Math.max(rx, -0.4),0.4);
        }
        double dx = y * Math.cos(yaw) - x * Math.sin(yaw);
        double dy = y * Math.sin(yaw) + x * Math.cos(yaw);

        double frontLeftPower = (dy + dx + rx);
        double backLeftPower = (dy - dx + rx);
        double frontRightPower = (dy - dx - rx);
        double backRightPower = (dy + dx - rx);

        double denominator = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
        denominator = Math.max(denominator, Math.abs(frontRightPower));
        denominator = Math.max(denominator, Math.abs(backRightPower));
        denominator = Math. max(denominator, 1.0);

        leftFront.setPower(frontLeftPower/denominator*12/Voltage);
        rightFront.setPower(frontRightPower/denominator*12/Voltage);
        leftBack.setPower(backLeftPower/denominator*12/Voltage);
        rightBack.setPower(backRightPower/denominator*12/Voltage);

    }


}