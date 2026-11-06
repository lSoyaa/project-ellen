package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import java.util.Random;

public class DefectiveLight extends Light implements Repairable{
    private int time = 0;
    private int nextDelay = generateDelay();
    private Disposable repairTimer;
    private Disposable loopDisposable;

    public DefectiveLight(){
        super();
        //setElectricityFlow(true);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        loopDisposable = new Loop<>(new Invoke<>(this::behavior)).scheduleFor(this);
    }

    private int generateDelay(){
        Random random = new Random();
        return random.nextInt(3000 - 500 + 1) + 500;
    }

    public void behavior(){
        int check = 100;
        time += check;
        if(time >= nextDelay){
            toggle();
            time = 0;
            nextDelay = generateDelay();
        }
    }

    private void resumeDefectiveBechaivor(){
        if(loopDisposable == null){
            loopDisposable = new Loop<>(new Invoke<>(this::behavior)).scheduleFor(this);
        }
        repairTimer = null;
    }

    @Override
    public boolean repair() {
        if(repairTimer != null){
            return false;
        }

        if(loopDisposable != null){
            loopDisposable.dispose();
            loopDisposable = null;
        }

        repairTimer = new ActionSequence<>(new Wait<>(10),
            new Invoke<>(this::resumeDefectiveBechaivor)).scheduleFor(this);
        return true;
    }
}
