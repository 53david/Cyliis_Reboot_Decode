package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.spin;
@Configurable
@TeleOp
public class LatchTest extends LinearOpMode {
    public static double pos = 0.16;
    public static double power = 0;
    @Override
    public void runOpMode(){
        Initializer.start(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            transfer.setPosition(pos);
            spin.setPower(power);
        }
    }
}
