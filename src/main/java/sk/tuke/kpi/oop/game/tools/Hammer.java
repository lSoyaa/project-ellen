package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool<Reactor>{

    public Hammer(){
        super(1);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public Hammer(int remainingUses){
        super(remainingUses);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    @Override
    public void useWith(Reactor actor) {
        if(actor == null) return;
        boolean isRepair = actor.repair();
        if(isRepair) {
            super.useWith(actor);
        }
    }
}
