package org.firstinspires.ftc.teamcode.Trajectories;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;
import static org.firstinspires.ftc.teamcode.OpModes.Autonomous.BlueClose.telemetryM;

import org.firstinspires.ftc.teamcode.Components.Intake.Spindexer;

public class CloseBlue {
    ElapsedTime timer = new ElapsedTime();
    public Follower follower;
    public Pose start = new Pose(32,134,Math.toRadians(270));
    public Pose spike1 = new Pose(24,85,Math.toRadians(180));
    public Pose spike2 = new Pose(24,60,Math.toRadians(180));
    public Pose gate = new Pose(15,60,Math.toRadians(150));
    public Pose shoot = new Pose(60,70,Math.toRadians(180));
    public Pose ctrlPt1 = new Pose(45,55);
    public Pose ctrlPt2 = new Pose(45,85);
    boolean isShootReady = false;
    public PathChain startShoot,shootSpike1,spike1Shoot,shootSpike2,spike2Shoot,shootGate,gateShoot;
    public enum State{
        StartShoot,
        ShootSpike1,
        Spike1Shoot,
        ShootSpike2,
        Spike2Shoot,
        ShootGate,
        GateShoot,
        TRANSITION,

    };
    State state ;
    State nextState = State.StartShoot;
    public CloseBlue(){
        follower.setStartingPose(start);
        follower.activateAllPIDFs(); /// nu e necesar
        state = State.StartShoot;
        buildPaths();

    }
    public void buildPaths(){
        startShoot = follower.pathBuilder()
                .addPath(new BezierLine(start,shoot))
                .setLinearHeadingInterpolation(start.getHeading(),shoot.getHeading())
                .build();
        shootSpike1 = follower.pathBuilder()
                .addPath(new BezierCurve(shoot,ctrlPt2,spike1))
                .setLinearHeadingInterpolation(shoot.getHeading(),spike1.getHeading())
                .build();
        spike1Shoot = follower.pathBuilder()
                .addPath(new BezierLine(spike1,shoot))
                .setLinearHeadingInterpolation(spike1.getHeading(),shoot.getHeading())
                .build();
        shootSpike2 = follower.pathBuilder()
                .addPath(new BezierCurve(shoot,ctrlPt1,spike1))
                .setLinearHeadingInterpolation(shoot.getHeading(),spike1.getHeading())
                .build();
        spike2Shoot = follower.pathBuilder()
                .addPath(new BezierLine(spike2,shoot))
                .setLinearHeadingInterpolation(spike2.getHeading(),shoot.getHeading())
                .build();
        shootGate = follower.pathBuilder()
                .addPath(new BezierCurve(shoot,ctrlPt1,gate))
                .setLinearHeadingInterpolation(shoot.getHeading(), gate.getHeading())
                .build();
        gateShoot = follower.pathBuilder()
                .addPath(new BezierLine(gate,shoot))
                .setLinearHeadingInterpolation(gate.getHeading(),shoot.getHeading())
                .build();
    }
    public void update(){
        telemetryM.addData("time",timer.seconds());
        telemetryM.update();
        switch (state){
            case TRANSITION:
                telemetryM.addLine("Path finished");
                if (follower.isBusy()){
                    timer.reset();
                }
                if (isShootReady){
                    Spindexer.state = Spindexer.State.SHOOT;
                }
                else if (timer.seconds()>1.5){
                    state = nextState;
                }
                break;
                case StartShoot:
                nextState = State.ShootSpike2;
                follower.followPath(startShoot);
                isShootReady = true;
                state = State.TRANSITION;
                break;
            case ShootSpike2:
                isShootReady = false;
                break;
            case Spike2Shoot:
                nextState = State.ShootGate;
                follower.followPath(spike2Shoot);
                isShootReady = true;
                state = State.TRANSITION;
                break;
            case ShootGate:
                nextState = State.GateShoot;
                follower.followPath(shootGate);
                isShootReady =  false;
                state = State.TRANSITION;
                break;
                case GateShoot:
                nextState = State.ShootGate;
                follower.followPath(gateShoot);
                isShootReady = true;
                state = State.TRANSITION;
                break;
        }
    }
}
