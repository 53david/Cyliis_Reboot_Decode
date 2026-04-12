package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Intake.ColorDetection;
import org.firstinspires.ftc.teamcode.Components.Intake.Storage;

@TeleOp
@Configurable
public class StorageTuner extends LinearOpMode {
    public static double power = 0;
    public static double pos = 0;
    public static boolean Turn120 = false;
    public static boolean Turn60 = false;
    public static boolean isTuningDone = false;
    Storage storage;
    ColorDetection colorDetection;
    @Override
    public void runOpMode() throws InterruptedException{
        storage = new Storage();
        colorDetection = new ColorDetection();
        waitForStart();
        while (opModeIsActive()) {
            storage.tune();
            transfer.setPosition(pos);
            intakeMotor.setPower(power);
            if (Turn120){
                storage.turn120();
                Turn120 = false;
            }
            if (Turn60){
                storage.turn60();
                Turn60 = false;
            }
            if (isTuningDone){
                storage.update();
                colorDetection.update();
            }
        }
    }
}
