package org.firstinspires.ftc.teamcode.Trajectories;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Node;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;

public class CloseBlue {
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

    public Pose2D beforeTakeSpike1Position = new Pose2D(-1270 , -450 , Math.PI/2);
    public Pose2D takeSpike1Position = new Pose2D(-1270 , 620 , Math.PI/2);
    Node shoot;
    public CloseBlue(HardwareMap hardwareMap){
        Chassis chassis = new Chassis();
        Intake intake = new Intake();
        Shooter shooter = new Shooter();
        Chassis.state = Chassis.State.PID;
        Intake.state = Intake.State.TRANSFER;
        Turret.state = Turret.AllienceState.BLUE;
        Initializer.start(hardwareMap);
        shoot = new Node("shoot");

    }
    public void update(){

    }

}
