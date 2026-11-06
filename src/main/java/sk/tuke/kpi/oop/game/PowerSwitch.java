package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {
    private Switchable device;

    public PowerSwitch(Switchable device){
        this.device = device;
        Animation controller = new Animation("sprites/switch.png");
        setAnimation(controller);
    }

    public Switchable getDevice() {
        return device;
    }

    public void switchOn(){
        if(device == null) return;
        device.turnOn();
        getAnimation().setTint(Color.WHITE);
    }

    public void switchOff(){
        if(device == null) return;
        device.turnOff();
        getAnimation().setTint(Color.GRAY);
    }

    /*void toggle(){
      if(!reactor.isOn()) reactor.turnOn();
      else reactor.turnOff();
    }*/

}
