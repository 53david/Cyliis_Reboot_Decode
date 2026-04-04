package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Pedro.Constants;
import org.firstinspires.ftc.teamcode.Trajectories.CloseBluePedro;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Robot;

@Autonomous
public class BlueClosePedro extends LinearOpMode {
    Follower follower;
    CloseBluePedro closeBlue;
    Robot robot;
    public static boolean isAutonomousActive;
    public static TelemetryManager telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    public void runOpMode() throws InterruptedException{
        follower = Constants.createFollower(hardwareMap);
        closeBlue = new CloseBluePedro();
        Initializer.start(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            isAutonomousActive = true;
            follower.update();
            robot.update();
            telemetryM.update();
        }

    }
}
