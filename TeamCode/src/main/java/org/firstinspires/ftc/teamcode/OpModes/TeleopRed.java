package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.FlyWheel;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@TeleOp
public class TeleopRed extends LinearOpMode {
    Chassis drive;
    Shooter shooter;
    Intake intake;
    Odo odo;
    @Override
    public void runOpMode(){
        isAutonomousActive = false;
        Initializer.start(hardwareMap);
        drive = new Chassis(Chassis.State.DRIVE);
        shooter = new Shooter();
        intake = new Intake();
        Turret.state = Turret.AllienceState.RED;
        Shooter.state = Shooter.State.SHOOT;
        waitForStart();
        while (opModeIsActive()){
            gm1.copy(gamepad1);
            gm2.copy(gamepad2);
            drive.update();
            shooter.update();
            intake.update();
            odo.update();
            prevgm1.copy(gm1);
            prevgm2.copy(gm2);

            telemetryM.addData("X",Odo.getX());
            telemetryM.addData("Y",Odo.getY());
            telemetryM.addData("Heading",Odo.getHeading());
            telemetryM.addData("Intake state", Intake.state);
            telemetryM.addData("Shooter state", Shooter.state);
            telemetryM.addData("Flywheel velocity", FlyWheel.getVelocity());
            telemetryM.update();
        }
    }
}
