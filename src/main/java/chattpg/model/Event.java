package chattpg.model;
import static chattpg.model.Task.requireNonBlank;

/**
 * A {@link Task} that spans a time range with distinct start (/from) and
 * end (/to) components.
 */
public class Event extends Task { 
    private final String from;
    private final String to;

    /**
     * Creates an event task with a start and end marker.
     *
     * @param description non-blank description
     * @param from non-blank start portion
     * @param to non-blank end portion (must differ from {@code from})
     * @throws IllegalArgumentException if any value is blank or start equals end
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = requireNonBlank(from, "event (/from)");
        this.to = requireNonBlank(to, "event (/to)");
        if (this.from.equals(this.to)) {
            throw new IllegalArgumentException("/from and /to must differ");
        }
    }

    /**
     * Returns the start marker.
     *
     * @return non-blank start value
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end marker.
     *
     * @return non-blank end value
     */
    public String getTo() {
        return to;
    }

    /**
     * String form for UI, prefixed with [E] and including the time range.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
    
}
