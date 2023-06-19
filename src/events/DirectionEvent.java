package events;

import java.util.EventObject;

public class DirectionEvent
    extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    private int direction;

    public DirectionEvent(Object source, int direction) {
        super(source);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
