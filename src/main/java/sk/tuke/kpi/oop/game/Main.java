package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenario.FirstStep;

public class Main {
    public static void main(String[] args){
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup, new LwjglBackend());
        Scene scene = new World("world");
        game.addScene(scene);
        FirstStep scenario = new FirstStep();
        scene.addListener(scenario);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, () -> game.stop());
        game.start();
    }

}
