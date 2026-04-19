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
        return MathFunctions.clamp(0.0000433298 * Math.pow(delta, 2)+ 0.0518419 * delta + 1262.769, 600,2200)
                +flywheelOffset;
    }

    public static double hoodAngle(double delta){
        return MathFunctions.clamp( (3.63972 * Math.pow(10,-9)) * Math.pow(delta,3) -0.0000186946 * Math.pow(delta,2)
                +0.0000186946 *delta -18.04131,0.11,0.45) + hoodOffset;
    }

}