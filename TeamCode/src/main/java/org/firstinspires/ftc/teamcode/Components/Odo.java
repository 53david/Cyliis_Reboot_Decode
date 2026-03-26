package org.firstinspires.ftc.teamcode.Components;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.normalizeRadians;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.pp;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

public class Odo {

    public Odo(){

        pp.setOffsets(48.366 * 0.0394,48.366 * 0.0394, DistanceUnit.INCH);
        pp.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pp.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
    }
    public void update(){
        pp.update();
    }

    public void setPosition(SparkFunOTOS.Pose2D pose){
        pp.setPosition(new Pose2D(DistanceUnit.MM,pose.x,pose.y, RADIANS, pose.h));
    }
    public static double cwDistance(double from, double to) {
        from = normalizeRadians(from);
        to = normalizeRadians(to);

        double dist = to - from;
        if (dist < 0) dist += 2 * Math.PI;
        return dist;
    }
    public static SparkFunOTOS.Pose2D getCurrentPosition(){
        double h = pp.getHeading(RADIANS);
        h = normalizeRadians(h);
        return new SparkFunOTOS.Pose2D(pp.getPosX(DistanceUnit.MM),pp.getPosY(DistanceUnit.MM), h);
    }
    public static double distance(){
        getCurrentPosition();
        return Math.sqrt(
                (getCurrentPosition().x - Turret.goalPositionX) * (getCurrentPosition().x - Turret.goalPositionX) +
                        (getCurrentPosition().y - Turret.goalPositionY) * (getCurrentPosition().y - Turret.goalPositionY)
        );

    }


}