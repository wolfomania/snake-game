package events;

import java.util.EventObject;

public class DifficultyEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    private int difficulty;

    public DifficultyEvent(Object source, int difficulty) {
        super(source);
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
