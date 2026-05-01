package org.firstinspires.ftc.teamcode.Math;

import com.bylazar.configurables.annotations.Configurable;
@Configurable
public class ShooterCalculator {

    public static double Kp = 0;
    public static double Ki = 0;
    public static double Kd = 0;
    public static double Ks = 0.14;
    public static double Kv = 0.00044;
    public static double Ka = 0.00635;
    public static double hoodOffset = 0.034;
    public static double fwOffset = 0;

    public static double fwVel(double delta) {
        return Math.clamp(0.0000706922*Math.pow(delta,2)-0.00691289*delta+1457.42598,1500,2400);
    }

    public static double hoodRegression(double velocity) {
        return Math.clamp((-0.000196031*Math.pow(velocity,2)+1.06616*velocity-1020.66802)*0.001,0.14,0.47) + hoodOffset;

    }
    public static double hoodAngle(double delta){
        return Math.clamp((-0.0000255468*Math.pow(delta,2)+0.232291*delta-84.02324)*0.001,0.14,0.47) + hoodOffset;
    }
}