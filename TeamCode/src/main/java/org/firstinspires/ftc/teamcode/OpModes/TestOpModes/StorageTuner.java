package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;


import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.encoder;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Intake.ColorDetection;
import org.firstinspires.ftc.teamcode.Components.Intake.Latch;
import org.firstinspires.ftc.teamcode.Components.Intake.Storage;
import org.firstinspires.ftc.teamcode.Components.Shooter.FlyWheel;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;

@TeleOp
@Configurable
public class StorageTuner extends LinearOpMode {
    public static double power = 0;
    public static double power2 =0 ;
    public static double pos = 0;
    public static boolean Turn120 = false;
    public static boolean Turn60 = false;
    public static double angle = 60;
    public static boolean isTuningDone = false;
    FlyWheel flyWheel;
    Storage storage;
    Latch latch;
    ColorDetection colorDetection;
    @Override
    public void runOpMode() throws InterruptedException{
        Initializer.start(hardwareMap);
        latch = new Latch();
        flyWheel = new FlyWheel();
        storage = new Storage();
        colorDetection = new ColorDetection();
        waitForStart();
        while (opModeIsActive()) {
            latch.update();
            storage.tune();
            intakeMotor.setPower(power);
            flyWheel.update();
            storage.update();
            colorDetection.update();
        }
    }
}
