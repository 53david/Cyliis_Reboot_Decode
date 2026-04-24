package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Wrappers.Initializer;

@TeleOp
public class ServoCalibration extends LinearOpMode {
    @Override
    public void runOpMode(){
        Initializer.start(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            Initializer.servo1.setPosition(0.5);
            Initializer.servo2.setPosition(0.5);
        }
    }
}
