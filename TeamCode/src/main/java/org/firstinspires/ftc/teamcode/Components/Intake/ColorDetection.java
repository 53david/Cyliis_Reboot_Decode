package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.OpModes.Teleop.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.color;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.proximitysensor;

public class ColorDetection {
    public float red;
    public float green;
    public float blue;
    public static int cup1 = 0, cup2 = 0, cup3 = 0;
    public double greenBall,purpleBall;
    public ColorDetection(){
        color.enableLed(true);
    }
    public static boolean isBallInStorage(){
        return proximitysensor.getState();
    }
    public float distance(float r1 , float g1  , float b1 , float r2 , float g2 , float b2)
    {
        return (float)Math.sqrt( (r1-r2)*(r1-r2) + (b1-b2)*(b1-b2) + (g1-g2)*(g1-g2));
    }
    public void update(){
        red = color.red();
        blue = color.blue();
        green = color.green();
        greenBall = distance(red,green,blue,0,255,0);
        purpleBall = distance(red,green,blue,175,0,175);
        if (purpleBall<=greenBall){ cup1 = 2;}
        else { cup1 = 1; }
        if (isBallInStorage() && Spindexer.nrBalls<3){
            cup3 = cup2;
            cup2 = cup1;
        }
        telemetryM.addData("Ball1",cup1);
        telemetryM.addData("Ball2",cup2);
        telemetryM.addData("Ball3",cup3);
        telemetryM.update();
    }
}
