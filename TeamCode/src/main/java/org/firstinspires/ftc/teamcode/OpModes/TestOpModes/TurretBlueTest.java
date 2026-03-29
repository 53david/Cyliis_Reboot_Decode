package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
@TeleOp
public class TurretBlueTest extends LinearOpMode {
    Turret turret;
    @Override
    public void runOpMode() throws InterruptedException{
        turret = new Turret();
        Turret.state = Turret.AllienceState.BLUE;
        waitForStart();
        while (opModeIsActive()){
            turret.update();
        }
    }

}
