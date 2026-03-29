package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Intake.Spindexer;
@TeleOp
public class StorageTuning extends LinearOpMode {
    public static double power = 0;
    public static boolean Turn120 = false;
    public static boolean Turn60 = false;
    Spindexer spindexer;
    @Override
    public void runOpMode() throws InterruptedException{
        waitForStart();
        while (opModeIsActive()) {
            spindexer.tune();
            intakeMotor.setPower(power);
            if (Turn120){ spindexer.turn120(); Turn120 = false;}
            if (Turn60){ spindexer.turn60(); Turn60 = false; }
        }
        }
}
