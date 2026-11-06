package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.actions.ActionSequence;

public class TimeBomb extends AbstractActor {
    private float timeForBomb;
    private boolean isBomb;
    private Animation activeBomb;
    private Animation boomBomb;

    public TimeBomb(float timeForBomb){
        this.timeForBomb = timeForBomb;
        this.isBomb = false;

        activeBomb = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);
        boomBomb = new Animation("sprites/small_explosion.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(new Animation("sprites/bomb.png", 16, 16));
    }


    public boolean isActivated(){
        return isBomb;
    }


    public void detonation(){
        setAnimation(boomBomb);
        new When<>(
            ()-> boomBomb.getCurrentFrameIndex() == boomBomb.getFrameCount() -1,
            new Invoke<>(()-> {
                Scene location = getScene();
                if(location == null) return;
                location.removeActor(this);
            }) ).scheduleFor(this);
    }

    public void activate(){
        if(isBomb) return;
        isBomb = true;
        setAnimation(activeBomb);
        new ActionSequence<>(new Wait<>(timeForBomb), new Invoke<>(this::detonation)).scheduleFor(this);
    }
}
