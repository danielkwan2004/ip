package chattpg.model;
import static chattpg.model.Task.requireNonBlank;

public class Event extends Task { 
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = requireNonBlank(from, "event (/from)");
        this.to = requireNonBlank(to, "event (/to)");
        if (this.from.equals(this.to)) {
            throw new IllegalArgumentException("/from and /to must differ");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
    
}
