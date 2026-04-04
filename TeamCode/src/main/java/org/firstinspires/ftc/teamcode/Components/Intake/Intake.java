package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.OpModes.TeleopBlue.telemetryM;

public class Intake {
    ActiveIntake activeIntake = new ActiveIntake();
    Spindexer spindexer = new Spindexer();
    Latch latch = new Latch();
    public enum State{
        IDLE,
        SHOOT,
        REVERSE,
        INTAKE,
        TRANSFER,
    };
    State state = State.IDLE;
    ColorDetection colorDetection = new ColorDetection();
    public void update(){
        stateUpdate();
        latch.update();
        spindexer.update();
        activeIntake.update();
        colorDetection.update();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                Latch.state = Latch.State.IDLE;
                Spindexer.state = Spindexer.State.IDLE;
                ActiveIntake.state = ActiveIntake.State.IDLE;
                break;
            case INTAKE:
                ActiveIntake.state = ActiveIntake.State.INTAKE;
                break;
            case REVERSE:
                ActiveIntake.state = ActiveIntake.State.REVERSE;
                break;
            case TRANSFER:
                Spindexer.state = Spindexer.State.TRANSFER;
                Latch.state = Latch.State.TRANSFER;
                break;
            case SHOOT:
                ActiveIntake.state = ActiveIntake.State.IDLE;
                Spindexer.state = Spindexer.State.SHOOT;
                Latch.state = Latch.State.TRANSFER;
                break;
        }
    }
}
