package events;

import java.util.EventObject;

public class EndGameEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EndGameEvent(Object source) {
        super(source);
    }
}
