package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Shooter.FlyWheel;
import org.firstinspires.ftc.teamcode.Components.Shooter.Hood;

@TeleOp
public class ShooterAjustments extends LinearOpMode {
    FlyWheel flyWheel;
    Hood hood;
    @Override
    public void runOpMode() throws InterruptedException{
           flyWheel = new FlyWheel();
           hood = new Hood();
           waitForStart();
           while (opModeIsActive()){
               flyWheel.update();
               hood.tuning();
           }
    }
}
