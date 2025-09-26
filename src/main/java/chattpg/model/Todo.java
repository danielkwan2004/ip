package chattpg.model;

/**
 * A simple {@link Task} with only a description and completion status.
 * No additional temporal metadata is stored.
 */
public class Todo extends Task {
    /**
     * Creates a todo task.
     *
     * @param description non-blank description
     * @throws IllegalArgumentException if description is null/blank
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * String form for UI, prefixed with [T].
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
