// a generic task has a description and status of done or not done
// the description is in the form of "[ ]" + description in a string format.
// this class is for each individual task, not the list of tasks.
public class Task {
    protected Boolean isDone;
    protected String description;

    public Task(String description) {
        this.isDone = false;
        this.description = description;
    }

    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + this.description;
        }
        return "[ ] " + this.description;
    }


    public void markTaskAsDone() {
        this.isDone = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("\t" + this.toString());
    }

    public void markTaskAsUndone() {
        this.isDone = false;
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("\t" + this.toString());
    }

    public String getDescription() {
        return this.description;
    }

}
