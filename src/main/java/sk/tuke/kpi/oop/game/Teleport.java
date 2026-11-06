package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean blocked;

    public Teleport(Teleport destination){
        setDestination(destination);
        this.blocked = false;
        setAnimation(new Animation("sprites/lift.png"));
    }

    public Teleport getDestination(){
        return destination;
    }

    public void setDestination(Teleport destinationTeleport) {
        if(destinationTeleport == this){
            return;
        }
        this.destination = destinationTeleport;
    }

    public void teleportPlayer(Player player){
        if(player == null) return;

        int newX = getPosX() + (getWidth() - player.getWidth()) / 2;
        int newY = getPosY() + (getHeight() - player.getHeight()) /2;

        player.setPosition(newX, newY);
    }

    private int getCenterXPlayer(Player player){
        return player.getPosX() + player.getWidth() / 2;
    }

    private int getCenterYPlayer(Player player){
        return player.getPosY() + player.getHeight() /2;
    }

    private boolean inside(int centerX, int centerY){
        return centerX >= getPosX() && centerX < getPosX() + getWidth() && centerY >= getPosY() && centerY < getPosY() + getHeight();
    }

    private void checkTeleport(){
        Scene location = getScene();
        if(location == null) return;

        Player player = location.getFirstActorByType(Player.class);
        if(player == null) return;

        if(blocked){
            if(!intersects(player)){
                blocked = false;
            }
            return;
        }
        int centerX = getCenterXPlayer(player);
        int centerY = getCenterYPlayer(player);
        if(inside(centerX, centerY) && destination != null){
            destination.blocked = true;
            destination.teleportPlayer(player);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::checkTeleport)).scheduleFor(this);
    }
}
