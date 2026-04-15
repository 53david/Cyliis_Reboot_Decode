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
import org.firstinspires.ftc.teamcode.Components.Intake.Storage;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;

@TeleOp
@Configurable
public class StorageTuner extends LinearOpMode {
    public static double power = 0;
    public static double power2 =0 ;
    public static double pos = 0;
    public static boolean Turn120 = false;
    public static boolean Turn60 = false;
    public static double angle = 1e9;
    public static boolean isTuningDone = false;
    Storage storage;
    ColorDetection colorDetection;
    @Override
    public void runOpMode() throws InterruptedException{
        Initializer.start(hardwareMap);
        storage = new Storage();
        colorDetection = new ColorDetection();
        waitForStart();
        while (opModeIsActive()) {
            storage.tune();
            transfer.setPosition(pos);
            intakeMotor.setPower(power);
            storage.update();
            colorDetection.update();

            telemetryM.addData("Turn120",Turn120);
            telemetryM.addData("Turn60",Turn60);
            telemetryM.addData("Rads",Storage.getAngle());
            telemetryM.addData("Angle", Math.toDegrees(Storage.getAngle()));
            telemetryM.addData("State",Storage.state);
            telemetryM.update();
        }
    }
}
