package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;


import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.ColorDetection;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.FlyWheel;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;

import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@TeleOp
public class TeleopBlue extends LinearOpMode {
    Intake intake;
    Chassis drive;
    Shooter shooter;
    Odo odo;

    @Override
    public void runOpMode() {
        isAutonomousActive = false;
        Initializer.start(hardwareMap);
        odo = new Odo();
        odo.reset();
        intake =new Intake();
        drive =new Chassis(Chassis.State.DRIVE);
        shooter =new Shooter();
        Turret.state = Turret.AllienceState.BLUE;
        Shooter.state = Shooter.State.SHOOT;
        waitForStart();
        while (opModeIsActive()) {
            gm1.copy(gamepad1);
            gm2.copy(gamepad2);
            intake.update();
            drive.update();
            shooter.update();
            odo.update();
            prevgm1.copy(gm1);
            prevgm2.copy(gm2);

            telemetryM.addData("ALIANCE",Turret.state);
            telemetryM.addData("X",Odo.getX());
            telemetryM.addData("Y",Odo.getY());
            telemetryM.addData("Heading",Odo.getHeading());
            telemetryM.addData("Intake state", Intake.state);
            telemetryM.addData("Shooter state", Shooter.state);
            telemetryM.addData("Flywheel velocity", FlyWheel.getVelocity());
            telemetryM.addData("Ball1", ColorDetection.ball1);
            telemetryM.addData("Ball2",ColorDetection.ball2);
            telemetryM.addData("Ball3",ColorDetection.ball3);
            telemetryM.update();
        }
    }
}