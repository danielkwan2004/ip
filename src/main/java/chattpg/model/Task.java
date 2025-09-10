package chattpg.model;

public class Task {
    private boolean done;
    protected String description;

    public Task(String description) {
        this.done = false;
        this.description = description;
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
        this.done = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("\t" + this.toString());
    }

    public void markTaskAsUndone() {
        this.done = false;
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("\t" + this.toString());
    }

    public String getDescription() {
        return this.description;
    }

}
