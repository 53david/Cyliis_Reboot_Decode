package org.firstinspires.ftc.teamcode.Math;

import com.bylazar.configurables.annotations.Configurable;
@Configurable
public class ShooterCalculator {

    public static double Kp = 0.005;
    public static double Ki = 0;
    public static double Kd = 0;
    public static double Ks = 0.13;
    public static double Kv = 0.00039;
    public static double hoodOffset = 0;
    public static double fwOffset = 0;

    public static double fwVel(double delta) {
        return Math.clamp(((8.52922 * Math.pow(10, -8)) * Math.pow(delta, 3)
                - 0.00050839 * Math.pow(delta, 2) + 1.25903 * delta + 519.65035)+ fwOffset,1480,2300);
    }

    public static double hoodAngle(double delta) {
        return Math.clamp(((0.0000241756 * Math.pow(delta, 3) - 0.122862
                * Math.pow(delta, 2) + 208.82546 * delta - 114884.567) * 0.0001)+hoodOffset,0.124,0.6);

    }
}