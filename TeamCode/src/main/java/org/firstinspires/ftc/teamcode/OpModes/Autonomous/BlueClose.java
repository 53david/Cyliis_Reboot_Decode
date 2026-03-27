package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Pedro.Constants;
import org.firstinspires.ftc.teamcode.Trajectories.CloseBlue;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Robot;

@Autonomous
public class BlueClose extends LinearOpMode {
    Follower follower;
    CloseBlue closeBlue;
    Robot robot;
    Intake intake;
    Shooter shooter;
    public static boolean isAutonomousActive;
    public static TelemetryManager telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    public void runOpMode() throws InterruptedException{
        follower = Constants.createFollower(hardwareMap);
        closeBlue = new CloseBlue();
        intake = new Intake();
        shooter = new Shooter();
        Initializer.start(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            isAutonomousActive = true;
            follower.update();
            intake.update();
            shooter.update();
            robot.update();
            telemetryM.update();
        }
    }
}
