package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;
@TeleOp
public class LatchTuner extends LinearOpMode {
    public double pos;
    @Override
    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()){
            transfer.setPosition(pos);
        }
    }
}
