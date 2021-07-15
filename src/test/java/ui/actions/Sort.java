package ui.actions;

import ui.Task;
import ui.Actor;

public class Sort implements Task {

    private String key = "";

    public Sort(String key) {
        this.key = key;
    }

    @Override
    public void performAs(Actor actor) {
        // actor.getDriver.find
        // click on value matching key
    }
}
