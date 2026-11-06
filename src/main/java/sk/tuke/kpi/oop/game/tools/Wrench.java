package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;


public class Wrench extends BreakableTool<DefectiveLight>{

    public Wrench(){
        super(2);
        setAnimation(new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith(DefectiveLight repairable) {
        if(repairable == null) return;
        boolean isRepair = repairable.repair();
        if(isRepair){
            super.useWith(repairable);
        }
    }
}
