package org.firstinspires.ftc.teamcode.Trajectories;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Intake.Spindexer;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Node;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;

public class CloseBlue {
    public Chassis chassis;
    public Intake intake;
    public Shooter shooter;
    public Odo odo;
    public static Pose2D shootPosition = new Pose2D(-1450, -460, Math.PI/2);
    public static Pose2D[] beforeSpike2Position = {
            new Pose2D(-1910, -100, Math.PI/2) ,
            new Pose2D(-2450, -100, Math.PI/2)
    };

    public static Pose2D[] Spike2Position = {
            new Pose2D(-1910, 765, Math.PI/2),
            new Pose2D(-2450, 765, Math.PI/2)
    };

    public static Pose2D beforeShootAfterCollectingPosition = new Pose2D(-1930, 600, Math.PI/2-0.3);
    public static Pose2D beforeIntakeWhileOpenGatePosition = new Pose2D(-1845, 620, Math.PI/2);
    public static Pose2D intakeWhileOpenGatePosition = new Pose2D(-1875, 700, Math.PI/2+0.4);

    public Pose2D beforeSpike1Position = new Pose2D(-1270 , -450 , Math.PI/2);
    public Pose2D Spike1Position = new Pose2D(-1270 , 620 , Math.PI/2);
    Node shoot,beforeSpike2,beforeOpenGate,beforeSpike1,openGate,Spike1,Spike2;
    public CloseBlue(HardwareMap hardwareMap){
         chassis = new Chassis();
         intake = new Intake();
         shooter = new Shooter();
         odo = new Odo();
        Chassis.state = Chassis.State.PID;
        Intake.state = Intake.State.TRANSFER;
        Turret.state = Turret.AllienceState.BLUE;
        Initializer.start(hardwareMap);
        shoot = new Node("shoot");
        beforeSpike2 = new Node("beforeSpike2");
        beforeOpenGate = new Node("beforeOpenGate");
        beforeSpike1 = new Node("beforeSpike1");

        shoot.addConditions(
                ()->{
                    if (Intake.state == Intake.State.INTAKE && intake.spindexer.IsStorageSpinning()){Intake.state = Intake.State.IDLE;}
                    chassis.setTargetPosition(shootPosition);
                    Shooter.state = Shooter.State.SHOOT;
                    if (chassis.inPosition(40,40,0.13) && Math.abs(Initializer.pp.getVelX(DistanceUnit.MM))<=25
                            && Math.abs(Initializer.pp.getVelX())<=25){
                        Intake.state = Intake.State.SHOOT;
                    }
                },
                ()->{
                    if (Spindexer.state == Spindexer.State.RESET) return true;
                    else return  false;
                },
                new Node[]{beforeSpike2, beforeOpenGate, beforeOpenGate, beforeSpike1}
        );
    }
    public void update(){
        odo.update();
        chassis.update();
        intake.update();
        shooter.update();
    }

}
