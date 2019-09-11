public class Todo extends Task {
    public static final String symbol = "T";

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toStringForHardDisk() {
        String[] datas = new String[3];
        datas[0] = Todo.symbol;
        datas[1] = isDone ? "1" : "0";
        datas[2] = description;

        return String.join(" | ", datas);
    }

    @Override
    public String toString() {
        return "[" + Todo.symbol + "]" + super.toString();
    }
}
