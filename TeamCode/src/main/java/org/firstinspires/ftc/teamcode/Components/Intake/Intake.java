package org.firstinspires.ftc.teamcode.Components.Intake;


public class Intake {
    public ColorDetection colorDetection = new ColorDetection();
    public ActiveIntake activeIntake;
    public Storage storage;

    public Latch latch;
    public enum State{
        IDLE,
        SHOOT,
        REVERSE,
        INTAKE,
        TRANSFER,
    };
    public static State state;
    public Intake(){
        state = State.IDLE;
        activeIntake = new ActiveIntake();
        storage = new Storage();
        latch = new Latch();
    }
    public void update(){
        latch.update();
        storage.update();
        activeIntake.update();
        colorDetection.update();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                Latch.state = Latch.State.IDLE;
                Storage.state = Storage.State.IDLE;
                ActiveIntake.state = ActiveIntake.State.IDLE;
                break;
            case INTAKE:
                ActiveIntake.state = ActiveIntake.State.INTAKE;
                break;
            case REVERSE:
                ActiveIntake.state = ActiveIntake.State.REVERSE;
                break;
            case TRANSFER:
                Storage.state = Storage.State.TRANSFER;
                Latch.state = Latch.State.TRANSFER;
                break;
            case SHOOT:
                ActiveIntake.state = ActiveIntake.State.IDLE;
                Storage.state = Storage.State.SHOOT;
                Latch.state = Latch.State.TRANSFER;
                break;
        }
    }
}
