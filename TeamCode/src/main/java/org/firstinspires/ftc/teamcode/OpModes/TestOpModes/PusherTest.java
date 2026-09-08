package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PusherTest extends LinearOpMode {
    @Override
    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()){
            telemetry.addLine("asdgjhasgdhjahdgjh");
            telemetry.update();
        }
    }
}
