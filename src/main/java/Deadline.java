import java.time.LocalDateTime;

public class Deadline extends Task {
    public static final String symbol = "D";

    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDate(by);
    }

    @Override
    public String toStringForHardDisk() {
        assert by != null : "Deadline date should exist.";

        String[] datas = new String[4];
        datas[0] = Deadline.symbol;
        datas[1] = isDone ? "1" : "0";
        datas[2] = description;
        datas[3] = dateToStringForHardDisk(by);

        return String.join(" | ", datas);
    }

    @Override
    public String toString() {
        assert by != null : "Deadline date should exist.";

        return "[" + Deadline.symbol + "]" + super.toString() + " (by: " + by + ")";
    }
}
