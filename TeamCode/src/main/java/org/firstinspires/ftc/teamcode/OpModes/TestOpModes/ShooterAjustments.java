package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Shooter.FlyWheel;
import org.firstinspires.ftc.teamcode.Components.Shooter.Hood;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@TeleOp
public class ShooterAjustments extends LinearOpMode {
    FlyWheel flyWheel;
    Hood hood;
    Odo odo;
    Chassis chassis;
    @Override
    public void runOpMode() throws InterruptedException{
           flyWheel = new FlyWheel();
           hood = new Hood();
           odo = new Odo();
           chassis = new Chassis(Chassis.State.DRIVE);
           Turret.state= Turret.AllienceState.BLUE;
           waitForStart();
           while (opModeIsActive()){
               flyWheel.tune();
               hood.tune();
               odo.update();
               telemetry.addData("Delta",Odo.distance());
               telemetry.update();
           }
    }
}
