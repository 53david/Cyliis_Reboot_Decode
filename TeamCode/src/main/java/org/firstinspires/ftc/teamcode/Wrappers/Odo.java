package org.firstinspires.ftc.teamcode.Wrappers;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.normalizeRadians;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.pp;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;

public class Odo {
    public Odo(){
        pp.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED , GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pp.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pp.setOffsets(129.503 , -78.001, DistanceUnit.MM);
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