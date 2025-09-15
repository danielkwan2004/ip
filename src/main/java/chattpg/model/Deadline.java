package chattpg.model;
import static chattpg.model.Task.requireNonBlank;

public class Deadline extends Task {

    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = requireNonBlank(by, "deadline (/by)");
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
