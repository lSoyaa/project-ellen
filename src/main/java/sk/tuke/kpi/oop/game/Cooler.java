package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable{
    private Animation CoolerAnimation;
    private boolean isCoolerTurn;
    private Reactor reactor;

    public Cooler(Reactor reactor){
        this.reactor = reactor;
        CoolerAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f);
        CoolerAnimation.pause();
        setAnimation(CoolerAnimation);
        isCoolerTurn = false;
    }

    public Reactor getReactor() {
        return reactor;
    }

    @Override
    public boolean isOn(){
        return isCoolerTurn;
    }

    public void turnOn(){
        isCoolerTurn = true;
        CoolerAnimation.play();
    }

    public void turnOff(){
        isCoolerTurn = false;
        CoolerAnimation.pause();
    }

    private void coolReactor(){
        if(isCoolerTurn && reactor != null){
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
