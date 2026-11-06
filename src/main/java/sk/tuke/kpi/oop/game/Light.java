package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private Animation diodeOn;
    private Animation diodeOff;
    private boolean electricityPower;
    private boolean isTurn;

    public Light(){
        diodeOn = new Animation("sprites/light_on.png");
        diodeOff = new Animation("sprites/light_off.png");
        setAnimation(diodeOff);
        this.electricityPower = false;
        this.isTurn = false;
    }
    @Override
    public boolean isOn(){
        return isTurn;
    }

    @Override
    public void turnOn() {
        if(isTurn == false){
            isTurn = true;
            updateAnimation();
        }
    }

    @Override
    public void turnOff() {
        if(isTurn == true){
            isTurn = false;
            updateAnimation();
        }
    }
     public void toggle(){
        isTurn = !isTurn;
        updateAnimation();
    }

    public void setElectricityFlow(boolean electricityPower){
        this.electricityPower = electricityPower;
        updateAnimation();
    }

    @Override
    public void setPowered(boolean powered) {
        setElectricityFlow(powered);
    }

    private void updateAnimation(){
        if(isTurn && electricityPower){
            setAnimation(diodeOn);
        }else{
            setAnimation(diodeOff);
        }
    }
}
