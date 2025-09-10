package chattpg.model;

public class Task {
    private boolean done;
    protected final String description;

    public Task(String description) {
        this.done = false;
        this.description = requireNonBlank(description, "description");
    }
    public static String requireNonBlank(String s, String label) {
        if (s == null) throw new IllegalArgumentException(label + " must not be null");
        String t = s.trim();
        if (t.isEmpty()) throw new IllegalArgumentException(label + " must not be empty");
        return t;
    }


    public boolean isDone() {
        return done;
    } 

    @Override
    public String toString() {
        if (done) {
            return "[X] " + this.description;
        }
        return "[ ] " + this.description;
    }


    public void markTaskAsDone() {
        if (this.done) {
            throw new IllegalStateException("Task already marked as done.");
        }
        this.done = true;
    }

    public void markTaskAsUndone() {
        if (!this.done) {
            throw new IllegalStateException("Task already marked as undone.");
        }
        this.done = false;
    }

    public String getDescription() {
        return this.description;
    }

}
