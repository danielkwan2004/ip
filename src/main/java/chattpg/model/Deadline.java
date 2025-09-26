package chattpg.model;

/**
 * A {@link Task} with an associated deadline ("/by" value).
 * Represents work that must be completed before or by a specific time/date.
 */
public class Deadline extends Task {

    private final String by;

    /**
     * Creates a deadline task.
     *
     * @param description non-blank description of the task
     * @param by non-blank deadline indicator (e.g., a date/time string)
     * @throws IllegalArgumentException if either parameter is null/blank
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = requireNonBlank(by, "deadline (/by)");
    }

    /**
     * Returns the raw deadline string provided by the user.
     *
     * @return deadline value (never blank)
     */
    public String getBy() {
        return by;
    }

    /**
     * String form used for UI listing, prefixed with [D] and including the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
