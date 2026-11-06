package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ChainBomb extends TimeBomb{
    public ChainBomb(float time){
        super(time);
    }

    @Override
    public void detonation() {
        float centerX = getPosX() + getWidth() /2f;
        float centerY = getPosY() + getHeight() /2f;

        super.detonation();

        Scene location = getScene();
        if(location == null) return;

        Ellipse2D.Float area = new Ellipse2D.Float(centerX - 50, centerY - 50, 100, 100);
        for(Actor actor : location.getActors()){
            if(!(actor instanceof  ChainBomb) || actor == this) {
                continue;
            }
            ChainBomb otherBomb = (ChainBomb) actor;
            if(otherBomb.isActivated()){
                continue;
            }

            Rectangle2D.Float bombBounds = new Rectangle2D.Float(otherBomb.getPosX(), otherBomb.getPosY(), otherBomb.getWidth(), otherBomb.getHeight());


            if(area.intersects(bombBounds)){
                otherBomb.activate();
            }
        }
    }
}
