package ui.test;

import org.junit.Test;
import ui.Actor;
import ui.Page;
import ui.actions.OpenThePage;
import ui.actions.Sort;

public class ExampleTest {

    @Test
    public void demo() {
        Actor guy = new Actor("New Guy");
       // guy.has()
        guy.attemptsTo(new OpenThePage(Page.HOME_PAGE));
        guy.attemptsTo(new Sort("xG"));

    }
}
