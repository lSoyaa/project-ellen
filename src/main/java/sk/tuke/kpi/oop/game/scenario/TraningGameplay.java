package sk.tuke.kpi.oop.game.scenario;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Computer;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Light;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.tools.Hammer;

public class TraningGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 64, 64);
        reactor.turnOn();
        Cooler cooler;
        cooler = new Cooler(reactor);
        scene.addActor(cooler, 128, 64);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);
        Hammer hammer = new Hammer();
        scene.addActor(hammer, 192, 64);
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(hammer);

        Light light = new Light();
        scene.addActor(light, 150, 150);
        reactor.addDevice(light);

        Computer computer = new Computer();
        scene.addActor(computer, 120, 120);
        reactor.addDevice(computer);
    }

}
