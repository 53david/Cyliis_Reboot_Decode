package org.firstinspires.ftc.teamcode.Math;

import com.bylazar.configurables.annotations.Configurable;
@Configurable
public class ShooterCalculator {

    public static double Kp = 0.003;
    public static double Ki = 0;
    public static double Kd = 0;
    public static double Ks = 0.13;
    public static double Kv = 0.00041;
    public static double Ka = 0.00475;
    public static double hoodOffset = 0.03375;
    public static double fwOffset = 40;

    public static double fwVel(double delta) {
        return Math.clamp(0.344376 * delta + 1115.454971400 + fwOffset,1500,2400);
    }

    public static double hoodRegression(double velocity) {
        return Math.clamp((-0.000196031*Math.pow(velocity,2)+1.06616*velocity-1020.66802)*0.001 + hoodOffset,0.12,0.5);

    }
    public static double hoodAngle(double delta){
        return Math.clamp((-0.0000255468*Math.pow(delta,2)+0.232291*delta-84.02324)*0.001 + hoodOffset,0.12,0.5);
    }
}