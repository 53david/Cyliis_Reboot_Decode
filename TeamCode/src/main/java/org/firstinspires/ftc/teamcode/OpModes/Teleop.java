package org.firstinspires.ftc.teamcode.OpModes;



import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm2;
import com.qualcomm.robotcore.hardware.Gamepad;


import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Chassis.Drive;


import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;

@TeleOp
public class Teleop extends LinearOpMode {
    Intake intake;
    Chassis chassis;
    Shooter shooter;
    public static TelemetryManager telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    public static double Voltage = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware();
        waitForStart();
        while (opModeIsActive()) {
            gm1.copy(gamepad1);
            gm2.copy(gamepad2);
            intake.update();
            chassis.update();
            shooter.update();
            telemetryM.update();
            prevgm1.copy(gm1);
            prevgm2.copy(gm2);
        }

    }
    private void initializeHardware() {
        Voltage = 12.0/hardwareMap.getAll(VoltageSensor.class).get(0).getVoltage();
        Initializer.start(hardwareMap);
        intake =new Intake();
        chassis =new Chassis();
        shooter =new Shooter();

    }
}