package org.firstinspires.ftc.teamcode.Wrappers;


import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Initializer {
    public static double Voltage = 0;
    public static boolean isAutonomousActive = false;
    public static TelemetryManager telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    public static DcMotorEx intakeMotor;
    public static DcMotorEx frontLeft;
    public static DcMotorEx frontRight;
    public static DcMotorEx backLeft;
    public static DcMotorEx backRight;
    public static DcMotorEx shoot1;
    public static DcMotorEx shoot2;
    public static DcMotorEx spin;
    public static ServoImplEx servo1;
    public static ServoImplEx servo2;
    public static Servo transfer;
    public static Servo hood;
    public static WebcamName webcam;
    public  static org.firstinspires.ftc.teamcode.Wrappers.GoBildaPinpointDriver pp;
    public static AnalogInput encoder;
    public static ColorSensor color;
    public static DigitalChannel proximitysensor;
    public static Gamepad prevgm1,prevgm2;
    public static Gamepad gm1,gm2;
    public static void start(HardwareMap hwMap){
        Voltage = 12.0/hwMap.getAll(VoltageSensor .class).get(0).getVoltage();
        prevgm1 = new Gamepad();
        prevgm2 = new Gamepad();
        gm1 = new Gamepad();
        gm2 = new Gamepad();
        pp = hwMap.get(GoBildaPinpointDriver.class,"pinpoint");
        intakeMotor = hwMap.get(DcMotorEx.class,"intake");
        transfer = hwMap.get(Servo.class,"transfer");
        frontLeft = hwMap.get(DcMotorEx.class,"leftFront");
        frontRight = hwMap.get(DcMotorEx.class,"rightFront");
        backRight = hwMap.get(DcMotorEx.class,"rightBack");
        backLeft = hwMap.get(DcMotorEx.class,"leftBack");
        shoot1 = hwMap.get(DcMotorEx.class,"shoot1");
        shoot2 = hwMap.get(DcMotorEx.class,"shoot2");
        spin = hwMap.get(DcMotorEx.class,"spin");
        servo1 = hwMap.get(ServoImplEx.class,"servo1");
        servo2 = hwMap.get(ServoImplEx.class,"servo2");
        webcam = hwMap.get(WebcamName.class,"Webcam 1");
        encoder = hwMap.get(AnalogInput.class,"encoder");
        color = hwMap.get(ColorSensor.class,"color");
        hood = hwMap.get(Servo.class,"hood");
        proximitysensor =hwMap.get(DigitalChannel.class,"proximitysensor");
        pp.resetPosAndIMU();
        pp.recalibrateIMU();

    }
}