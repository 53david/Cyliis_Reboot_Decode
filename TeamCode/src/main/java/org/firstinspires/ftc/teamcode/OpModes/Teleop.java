package org.firstinspires.ftc.teamcode.OpModes;



import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;


import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Components.Drive;


import org.firstinspires.ftc.teamcode.Components.FlyWheel;
import org.firstinspires.ftc.teamcode.Components.Intake;
import org.firstinspires.ftc.teamcode.Components.Turret;
import org.firstinspires.ftc.teamcode.Components.Vision;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;

@TeleOp
public class Teleop extends LinearOpMode {
    public static IMU imu;
    private Drive chassis;
    private Turret turret;
    private Intake intake;
    private FlyWheel flyWheel;
    private Vision vision;
    GoBildaPinpointDriver gobilda;
    public static TelemetryManager telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    public static Gamepad prevgm1,prevgm2;
    public static Gamepad gm1,gm2;
    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware();
        prevgm1 = new Gamepad();
        prevgm2 = new Gamepad();
        gm1 = new Gamepad();
        gm2 = new Gamepad();
        waitForStart();
        while (opModeIsActive()) {
            gm1.copy(gamepad1);
            gm2.copy(gamepad2);
            intake.update();
            turret.update();
            chassis.update();
            flyWheel.update();
            vision.update();
            telemetryM.update();
            prevgm1.copy(gm1);
            prevgm2.copy(gm2);

        }

    }
    private void initializeHardware() {
        Initializer.start(hardwareMap);

        gobilda = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        gobilda.resetPosAndIMU();
        gobilda.recalibrateIMU();
        chassis = new Drive();
        intake = new Intake();
        flyWheel = new FlyWheel();
        vision = new Vision();
        turret = new Turret(gobilda);
        Turret.Voltage = 12.0/hardwareMap.getAll(VoltageSensor.class).get(0).getVoltage();
        Drive.Voltage = 12.0/hardwareMap.getAll(VoltageSensor.class).get(0).getVoltage();
    }
}