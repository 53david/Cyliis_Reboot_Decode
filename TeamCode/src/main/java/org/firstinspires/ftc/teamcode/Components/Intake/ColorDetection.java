package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.OpModes.TeleopBlue.telemetryM;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.color;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.proximitysensor;

public class ColorDetection {
    public float red = 0;
    public float green = 0;
    public float blue = 0;
    public int ball1 = 0, ball2 = 0, ball3 = 0;
    public double greenBall = 0,purpleBall = 0;
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

        if (purpleBall<=greenBall){ ball1 = 2;}
        else { ball1 = 1; }
        if (isBallInStorage() && Spindexer.state == Spindexer.State.BALL){
            ball3 = ball2; ball2 = ball1;
        }
        if (Spindexer.state == Spindexer.State.SHOOT){
            ball1 = 0; ball2 = 0; ball3 = 0;
        }
        telemetryM.addData("Ball1",ball1);
        telemetryM.addData("Ball2",ball2);
        telemetryM.addData("Ball3",ball3);
        telemetryM.update();
    }
}
