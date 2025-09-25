package chattpg.logic.exceptions;

/**
 * Thrown when a user references a task index that is outside the bounds of the
 * current task list (e.g., less than 1 or greater than the number of tasks).
 * The message usually contains guidance such as the valid range.
 */
public class TaskIndexOutOfBoundsException extends Exception {
    /**
     * Constructs a new exception indicating the index issue.
     *
     * @param message details about the invalid index and expected range
     */
    public TaskIndexOutOfBoundsException(String message) {
        super(message);
    }
}
