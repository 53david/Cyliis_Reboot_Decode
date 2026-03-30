package org.firstinspires.ftc.teamcode.Trajectories;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarBlue {
    /// TODO: Continua auto
    public Follower follower;
    public Pose start = new Pose(56,12,Math.toRadians(90));
    public Pose spike = new Pose(24,36,Math.toRadians(180));
    public Pose shoot = new Pose(50,15,Math.toRadians(180));
    public Pose loadingZone = new Pose (10,12,Math.toRadians(180));
    public Pose feed = new Pose(12,24,Math.toRadians(140));
    public Pose ctrlPt = new Pose(50,35);
    public PathChain startSpike,spikeShoot,shootLoadingZone,loadingZoneShoot;
    public enum State{
        START,
        StartSpike,
        SpikeShoot,
        ShootLoadingZone,
        LoadingZoneShoot,
        TRANSITION
    };
    public void buildPaths(){
        startSpike = follower.pathBuilder()
                .addPath(new BezierCurve(start,ctrlPt,spike))
                .setLinearHeadingInterpolation(start.getHeading(),spike.getHeading())
                .build();
        spikeShoot = follower.pathBuilder()
                .addPath(new BezierLine(spike,shoot))
                .setLinearHeadingInterpolation(spike.getHeading(),shoot.getHeading())
                .build();
        shootLoadingZone = follower.pathBuilder()
                .addPath(new BezierLine(shoot,loadingZone))
                .setLinearHeadingInterpolation(shoot.getHeading(),loadingZone.getHeading())
                .build();
        loadingZoneShoot = follower.pathBuilder()
                .addPath(new BezierLine(loadingZone,shoot))
                .setLinearHeadingInterpolation(loadingZone.getHeading(), shoot.getHeading())
                .build();
    }
    State state;
    public FarBlue(){
        follower.setStartingPose(start);
        follower.activateAllPIDFs();
        state = State.START;
        buildPaths();
    }
    public void update(){
        switch (state){
            case START:

                break;
        }
    }
}
