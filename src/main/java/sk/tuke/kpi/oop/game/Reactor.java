package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private Set<EnergyConsumer> devices = new HashSet<>();
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation breakAnimation;
    private Animation offAnimation;
    private Animation reactorExtinguished;
    private boolean isReactorOn;

    private Light light;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        // create animation object
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG );
        breakAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png");
        reactorExtinguished = new Animation("sprites/reactor_extinguished.png");
        setAnimation(offAnimation);
        //turnOff();
       //pdateAnimation();

    }

    private int calculateIncrease(int increase){
        if(damage >= 33 && damage <= 66){
            return (int)Math.ceil(increase * 1.5);
        }
        if(damage > 66){
            return (int)Math.ceil(increase * 2);
        }
        return increase;
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage(){
        return this.damage;
    }

    public void increaseTemperature(int increase){
        if(increase < 0) return;
        if(!isReactorOn) return;
        if(damage == 100){
            turnOff();
            return;
        }
        temperature += calculateIncrease(increase);

        if(temperature >= 6000){
            damage = 100;
            turnOff();
            updateAnimation();
        }else if(temperature > 4000){
            updateAnimation();
        }
        if(temperature >= 2000){
            int damageNew = (int)Math.floor((temperature - 2000) / 40.0);
            damage = Math.max(damage, damageNew);
            damage = Math.min(damage, 100);
        }
    }
    public void decreaseTemperature(int decrease){
        if(decrease < 0) return;
        if(!isReactorOn) return;
        if(damage == 100){
            turnOff();
            return;
        }
        int actual = decrease;
        if(damage >= 50){
            actual = decrease / 2;
        }
        temperature = temperature - actual;
        updateAnimation();
    }

    private void updateAnimation(){
        float dur =  0.05f - (damage/100.0f)* 0.04f;
        dur = Math.max(dur, 0.01f);
        hotAnimation.setFrameDuration(dur);
        if(damage == 100){
            setAnimation(breakAnimation);
        }else if(!isOn()){
            setAnimation(offAnimation);
        }else if(temperature > 4000){
            setAnimation(hotAnimation);
        }else{
            setAnimation(normalAnimation);
        }
    }

    @Override
    public boolean repair(){
        if(damage < 100 && damage > 0){
            int repair = damage - 50;
            int newTemperature = repair * 40 + 2000;
            damage = repair;
            if(damage < 0) damage = 0;
            if(newTemperature < temperature){
                temperature = (int) newTemperature;
            }
            updateAnimation();
            return true;
        }
        return false;
    }

    public void turnOn(){
        if(damage >= 100) return;
        isReactorOn = true;
        updateDevices();
        updateAnimation();
        if(light != null){
            light.setElectricityFlow(true);
        }
    }

    public void turnOff(){
        isReactorOn = false;
        updateDevices();
        updateAnimation();
        if(light != null){
            light.setElectricityFlow(false);
        }
    }

    public boolean isOn(){
        return isReactorOn;
    }

    public void addLight(Light light){
        if(light == null) return;
        this.light = light;
        if(isReactorOn && damage < 100){
            light.setElectricityFlow(true);
        }else{
            light.setElectricityFlow(false);
        }
    }

    public void removeLight(){
        if(light == null) return;
        light.setElectricityFlow(false);
        this.light = null;
    }

    public boolean extinguish(){
        if(this.temperature > 4000) {
            this.temperature = 4000;
            setAnimation(reactorExtinguished);
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene( Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public void addDevice(EnergyConsumer device){
        if(device == null) return;
        devices.add(device);

        if(isReactorOn){
            device.setPowered(true);
        }
    }

    public void removeDevice(EnergyConsumer device){
        if(device == null) return;
        devices.remove(device);
        device.setPowered(false);
    }

    private void updateDevices(){
        for(EnergyConsumer device : devices){
            device.setPowered(isReactorOn);
        }
    }

}
