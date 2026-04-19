package org.firstinspires.ftc.teamcode.Wrappers;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.math.MathFunctions;
@Configurable
public class ShooterConstants {

    public static double Kp = 0.003;
    public static double Ki = 0;
    public static double Kd = 0;
    public static double Ks = 0.15;
    public static double Kv = 0.0004;
    public static double Ka = 0.01;
    public static double vel = 0;
    public static double flywheelOffset = 0;
    public static double hoodOffset = 0;
    public static double fwVel(double delta) {
        return MathFunctions.clamp(0.0000247737 * Math.pow(delta, 2)+ 0.106755 * delta + 1187.38005, 600,2100)
                +flywheelOffset;
    }

    public static double hoodAngle(double delta){
        return MathFunctions.clamp( (1.25 * Math.pow(10,-9)) * Math.pow(delta,3) - 0.00000694643 * Math.pow(delta,2)
                +0.0130607 *delta -7.88929,0.11,0.55) + hoodOffset;
    }

}