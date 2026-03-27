package org.firstinspires.ftc.teamcode.Wrappers;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
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
    public static DcMotorEx intakeMotor;
    public static DcMotorEx leftFront;
    public static DcMotorEx rightFront;
    public static DcMotorEx leftBack;
    public static DcMotorEx rightBack;
    public static ServoImplEx servo1;
    public static ServoImplEx servo2;
    public static DcMotorEx shoot1;
    public static DcMotorEx shoot2;
    public static WebcamName webcam;
    public static Servo transfer;
    public static DcMotorEx spin;
    public static GoBildaPinpointDriver pp;
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
        leftFront = hwMap.get(DcMotorEx.class,"leftFront");
        rightFront = hwMap.get(DcMotorEx.class,"rightFront");
        rightBack = hwMap.get(DcMotorEx.class,"rightBack");
        leftBack = hwMap.get(DcMotorEx.class,"leftBack");
        shoot1 = hwMap.get(DcMotorEx.class,"shoot1");
        shoot2 = hwMap.get(DcMotorEx.class,"shoot2");
        spin = hwMap.get(DcMotorEx.class,"spin");
        servo1 = hwMap.get(ServoImplEx.class,"servo1");
        servo2 = hwMap.get(ServoImplEx.class,"servo2");
        webcam = hwMap.get(WebcamName.class,"Webcam 1");
        encoder = hwMap.get(AnalogInput.class,"encoder");
        color = hwMap.get(ColorSensor.class,"color");
        proximitysensor =hwMap.get(DigitalChannel.class,"proximitysensor");
        pp.resetPosAndIMU();
        pp.recalibrateIMU();

    }
}