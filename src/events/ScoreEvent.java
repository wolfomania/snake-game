package events;

import java.util.EventObject;

public class ScoreEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    private int score;

    public ScoreEvent(Object source, int score) {
        super(source);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
