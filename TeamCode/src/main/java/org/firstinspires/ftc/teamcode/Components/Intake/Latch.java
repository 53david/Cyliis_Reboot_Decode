package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Latch {
    public enum State {
        IDLE,
        TRANSFER,
    };
    public static State state;
    public static double transPos = 0.385;
    public static double idlePos = 0.16;
    public Latch(){
        transfer.setPosition(idlePos);
        state = State.IDLE;
    }
    public void update(){
        stateUpdate();

    }
    public void stateUpdate(){
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