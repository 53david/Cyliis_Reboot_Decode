package org.firstinspires.ftc.teamcode.Components.Intake;


public class Intake {
    public ActiveIntake activeIntake;
    public Spindexer spindexer;

    public Latch latch;
    public enum State{
        IDLE,
        SHOOT,
        REVERSE,
        INTAKE,
        TRANSFER,
    };
    public static State state;
    ColorDetection colorDetection = new ColorDetection();
    public Intake(){
        state = State.IDLE;
        activeIntake = new ActiveIntake();
        spindexer = new Spindexer();
        latch = new Latch();
    }
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
