import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public abstract class Task {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    private String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns the string presentation of this task in hard disk.
     */
    public abstract String toStringForHardDisk();

    protected LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    protected String dateToStringForHardDisk(LocalDateTime date) {
        return formatter.format(date);
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
