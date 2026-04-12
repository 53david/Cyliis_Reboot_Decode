package org.firstinspires.ftc.teamcode.Components.Intake;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Latch {
    public enum State {
        IDLE,
        TRANSFER,
    };
    public static State state;
    public static double transPos = 0;
    public static double idlePos = 0;
    public Latch(){
        transfer.setPosition(idlePos);
        state = State.IDLE;
    }
    public void update(){
        stateUpdate();
        if (!isAutonomousActive && Spindexer.state == Spindexer.State.TRANSFER){
            state = State.TRANSFER;
        }
        if (!isAutonomousActive && Spindexer.state == Spindexer.State.RESET){
            state = State.IDLE;
        }
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