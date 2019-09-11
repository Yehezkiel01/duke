import java.util.Date;

public class Deadline extends Task {
    protected Date by;

    public Deadline(String description, String by) throws DukeException {
        super(description);
        this.by = parseDate(by);
    }

    @Override
    public String toStringForHardDisk() {
        assert by != null : "Deadline date should exist.";

        String[] datas = new String[4];
        datas[0] = "D";
        datas[1] = isDone ? "1" : "0";
        datas[2] = description;
        datas[3] = dateToStringForHardDisk(by);

        return String.join(" | ", datas);
    }

    @Override
    public String toString() {
        assert by != null : "Deadline date should exist.";

        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
