import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReminderCommand extends Command {
    @Override
    public boolean isExit() {
        return false;
    }

    private static final int limit = 3;

    /**
     * Selects several tasks which are closest to their date of event or deadline.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws DukeException {
        ArrayList<Task> selectedTasks = selectTasks(tasks);
        if (selectedTasks.size() == 0) {
            return "Currently there is no upcoming tasks.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Here are some of your upcoming tasks.\n");
        for (int i = 0; i < selectedTasks.size(); ++i) {
            builder.append("- " + selectedTasks.get(i) + "\n");
        }
        return builder.toString();
    }

    private ArrayList<Task> selectTasks(TaskList tasks) throws DukeException {
        LocalDateTime[] dates = new LocalDateTime[tasks.size() + 1];
        ArrayList<Integer> selectedTasks = new ArrayList<Integer>();

        for (int i = 1; i <= tasks.size(); ++i) {
            Task currentTask = tasks.get(i);
            if (!isValid(currentTask)) {
                continue;
            }

            dates[i] = (currentTask instanceof Event) ? ((Event) currentTask).at : ((Deadline) currentTask).by;
            selectedTasks.add(i);
            for (int j = selectedTasks.size() - 2; j >= 0; --j) {
                int now = selectedTasks.get(j + 1);
                int prev = selectedTasks.get(j);
                if (!dates[now].isBefore(dates[prev])) {
                    break;
                }
                //swap now and prev
                selectedTasks.set(j + 1, prev);
                selectedTasks.set(j, now);
            }

            if (selectedTasks.size() > limit) {
                selectedTasks.remove(selectedTasks.size() - 1);
            }
        }
        assert selectedTasks.size() <= limit : "should not select more than limit.";

        ArrayList<Task> ret = new ArrayList<Task>();
        for (int i = 0; i < selectedTasks.size(); ++i) {
            ret.add(tasks.get(selectedTasks.get(i)));
        }
        return ret;
    }

    private boolean isValid(Task task) {
        if (task.getIsDone()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            if (deadline.by.isBefore(currentTime)) {
                return false;
            }
        } else if (task instanceof Event) {
            Event event = (Event) task;
            if (event.at.isBefore(currentTime)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}
