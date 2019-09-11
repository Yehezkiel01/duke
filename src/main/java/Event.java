import java.util.Date;

public class Event extends Task {
    public static final String symbol = "E";

    protected Date at;

    public Event(String description, String at) throws DukeException {
        super(description);
        this.at = parseDate(at);
    }

    @Override
    public String toStringForHardDisk() {
        assert at != null : "Event date should exist.";

        String[] datas = new String[4];
        datas[0] = Event.symbol;
        datas[1] = isDone ? "1" : "0";
        datas[2] = description;
        datas[3] = dateToStringForHardDisk(at);

        return String.join(" | ", datas);
    }

    @Override
    public String toString() {
        assert at != null : "Event date should exist.";

        return "[" + Event.symbol + "]" + super.toString() + " (at: " + at + ")";
    }
}
