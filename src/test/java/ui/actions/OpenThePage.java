package ui.actions;

import ui.Task;
import ui.Actor;
import ui.Page;

public class OpenThePage implements Task {
    private Page page;

    public OpenThePage(Page page) {
        this.page = page;
    }

    @Override
    public void performAs(Actor actor) {
        //actot.getdriver.get(Page.url);
        System.out.println("doing something");
    }

}
