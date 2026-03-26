package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.color;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.proximitysensor;

public class ColorDetection {
    public double red;
    public double green;
    public double blue;
    public ColorDetection(){
        color.enableLed(true);
    }
    public static boolean isBallInStorage(){
        return proximitysensor.getState();
    }
    public void update(){
        red = color.red();
        blue = color.blue();
        green = color.green();

    }
}
