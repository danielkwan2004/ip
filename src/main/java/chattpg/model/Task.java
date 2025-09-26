package chattpg.model;

/**
 * Minimal base class representing a task with a textual description and
 * a completion flag. Subclasses (e.g., {@link Todo}, {@link Deadline},
 * {@link Event}) extend this with additional attributes.
 */
public class Task {
    private boolean done;
    protected final String description;

    /**
     * Creates a new task with the given description, initially not done.
     *
     * @param description non-blank description of the task
     * @throws IllegalArgumentException if description is null/blank
     */
    public Task(String description) {
        this.done = false;
        this.description = requireNonBlank(description, "description");
    }
    /**
     * Ensures a string is non-null and non-blank.
     *
     * @param s the string to validate
     * @param label name for error context if validation fails
     * @return trimmed string if valid
     * @throws IllegalArgumentException if null or blank
     */
    public static String requireNonBlank(String s, String label) {
        if (s == null) throw new IllegalArgumentException(label + " must not be null");
        String t = s.trim();
        if (t.isEmpty()) throw new IllegalArgumentException(label + " must not be empty");
        return t;
    }

    /**
     * Indicates whether the task has been completed.
     *
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return done;
    } 

    /**
     * Human-readable status with a checkbox indicating done/undone and the
     * task description.
     */
    @Override
    public String toString() {
        if (done) {
            return "[X] " + this.description;
        }
        return "[ ] " + this.description;
    }

    /**
     * Marks the task as done.
     *
     * @throws IllegalStateException if already marked done
     */
    public void markTaskAsDone() {
        if (this.done) {
            throw new IllegalStateException("Task already marked as done.");
        }
        this.done = true;
    }

    /**
     * Marks the task as not done.
     *
     * @throws IllegalStateException if already not done
     */
    public void markTaskAsUndone() {
        if (!this.done) {
            throw new IllegalStateException("Task already marked as undone.");
        }
        this.done = false;
    }

    /**
     * Returns the raw description text for persistence and display.
     *
     * @return non-blank description
     */
    public String getDescription() {
        return this.description;
    }

}
