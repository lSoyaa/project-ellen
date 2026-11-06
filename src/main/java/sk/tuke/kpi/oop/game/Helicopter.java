package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    //private Animation HelicopterAnimation;
    public Helicopter(){
           setAnimation(new Animation("sprites/heli.png", 64, 64, 0.1f, Animation.PlayMode.LOOP));
    }


    private void movement(Player player){
            if(player != null) {
                int currentX = getPosX();
                int currentY = getPosY();

                int playerX = player.getPosX();
                int playerY = player.getPosY();

                if (currentX < playerX) {
                    currentX++;
                } else if (currentX > playerX) {
                    currentX--;
                }

                if (currentY < playerY) {
                    currentY++;
                } else if (currentY > playerY) {
                    currentY--;
                }

                setPosition(currentX, currentY);

                if(intersects(player)){
                    int energy = player.getEnergy() - 1;
                    player.setEnergy(energy);
                }
            }
    }

    public void searchAndDestroy(){
        if(getScene() == null) return;
        Player player  = getScene().getFirstActorByType(Player.class);
        if(player == null) return;

        new Loop<>(new Invoke<>(()->movement(player))).scheduleFor(this);
    }
}
