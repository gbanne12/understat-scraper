package ui;


/***
 * Actor is responsible for driving the tests
 * Boa constrictor - examples
 * Actor.attempsTo(Task task)
 * Actor.can(Ability ability)
 *  Actor
 */
public class Actor {

    private String name;

    public Actor(String name) {
        this.name = name;
    }

//    public Actor has(Ability ability) {
  //      ability.performAs(this);
  //      return this;
 //   }

    public Actor attemptsTo(Task task) {
        task.performAs(this);
        return this;
    }

    public Actor should(Task task) {
        task.performAs(this);
        return this;
    }
}
