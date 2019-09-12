public class Parser {
    /**
     * Parses user command.
     * 
     * @param command User plain command.
     * @return Command object which represents the user command.
     * @throws DukeException if the parsing process fails.
     */
    public static Command parse(String command) throws DukeException {
        if (command.equals("help")) {
            return new HelpCommand();
        } else if (command.equals("bye")) {
            return new ByeCommand();
        } else if (command.equals("list")) {
            return new ListCommand();
        } else if (command.equals("reminder")) {
            return new ReminderCommand();
        } else if (command.matches("^done \\d+$")) {
            return parseDoneCommand(command);
        } else if (command.matches("^delete \\d+$")) {
            return parseDeleteCommand(command);
        } else if (command.startsWith("deadline")) {
            return parseDeadlineTask(command);
        } else if  (command.startsWith("todo")) {
            return parseTodoTask(command);
        } else if (command.startsWith("event")) {
            return parseEventTask(command);
        } else if (command.startsWith("find")) {
            return parseFindCommand(command);
        } else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static DoneCommand parseDoneCommand(String command) throws DukeException {
        int index = Integer.parseInt(command.split(" ")[1]);
        return new DoneCommand(index);
    }

    private static DeleteCommand parseDeleteCommand(String command) throws DukeException {
        int index = Integer.parseInt(command.split(" ")[1]);
        return new DeleteCommand(index);
    }

    private static FindCommand parseFindCommand(String command) throws DukeException {
        try {
            return new FindCommand(command.split(" ", 2)[1]);
        } catch (IndexOutOfBoundsException ex) {
            throw new DukeException("Find command needs a keyword");
        }
    }

    private static AddCommand parseDeadlineTask(String command) throws DukeException {
        try {
            String suffix = command.split(" ", 2)[1];
            String description = suffix.split(" /by ", 2)[0];
            String by = suffix.split(" /by ", 2)[1];

            Deadline task = new Deadline(description, by);
            return new DeadlineCommand(task);
        } catch (IndexOutOfBoundsException ex) {
            throw new DukeException("Deadline task formatting error.");
        }
    }

    private static AddCommand parseTodoTask(String command) throws DukeException {
        try {
            String description = command.split(" ", 2)[1];

            Todo task = new Todo(description);
            return new TodoCommand(task);
        } catch (IndexOutOfBoundsException ex) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
    }

    private static AddCommand parseEventTask(String command) throws DukeException {
        try {
            String suffix = command.split(" ", 2)[1];
            String description = suffix.split(" /at ", 2)[0];
            String at = suffix.split(" /at ", 2)[1];

            Event task = new Event(description, at);
            return new EventCommand(task);
        } catch (IndexOutOfBoundsException ex) {
            throw new DukeException("Event task formatting error.");
        }
    }
}
