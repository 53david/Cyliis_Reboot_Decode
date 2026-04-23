package org.firstinspires.ftc.teamcode.Components.Shooter;

public class Shooter {
    FlyWheel flyWheel;
    Hood hood;
    public enum State{
        IDLE,
        SHOOT,
    }
    public static State state = State.SHOOT;
    public Shooter(){
        flyWheel =new FlyWheel();
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
    }
}
