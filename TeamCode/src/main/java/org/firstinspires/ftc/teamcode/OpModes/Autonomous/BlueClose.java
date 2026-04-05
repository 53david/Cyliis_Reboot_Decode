package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import org.firstinspires.ftc.teamcode.Trajectories.CloseBlue;

@Autonomous
public class BlueClose extends LinearOpMode {
    CloseBlue closeBlue;
    @Override
    public void runOpMode(){
        isAutonomousActive = true;
        closeBlue = new CloseBlue(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            closeBlue.update();
        }
    }

}
