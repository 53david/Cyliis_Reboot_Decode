package org.firstinspires.ftc.teamcode.Components;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;
public class Latch {
    enum State {
        IDLE,
        TRANSFER,
    };
    State state;
    public static double transPos = 0;
    public static double idlePos = 0;
    public Latch(){
        transfer.setPosition(idlePos);
        state = State.IDLE;
    }
    public void update(){
        switch (state){
            case IDLE:
                transfer.setPosition(idlePos);
                break;
            case TRANSFER:
                transfer.setPosition(transPos);
                break;
        }
    }
}