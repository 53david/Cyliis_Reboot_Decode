package org.firstinspires.ftc.teamcode.Components.Shooter;

import org.firstinspires.ftc.teamcode.Components.Intake.Intake;

public class Shooter {
    public FlyWheel flyWheel;
    public Turret turret;
    public Hood hood;
    public enum State{
        IDLE,
        SHOOT,
    }
    public static State state = State.IDLE;
    public Shooter(){
        flyWheel =new FlyWheel();
        turret =new Turret();
        hood = new Hood();
    }

    public void updateState(){
        switch (state){
            case IDLE:
                Hood.state = Hood.State.IDLE;
                FlyWheel.state = FlyWheel.State.IDLE;
                break;
            case SHOOT:
                Hood.state = Hood.State.SHOOT;
                FlyWheel.state = FlyWheel.State.SHOOT;
                break;
        }

    }
    public void update(){
        updateState();
        hood.update();
        flyWheel.update();
        turret.update();
    }
}
