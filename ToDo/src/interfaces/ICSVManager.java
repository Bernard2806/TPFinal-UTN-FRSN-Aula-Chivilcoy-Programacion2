package interfaces;

import java.util.HashMap;
import models.Task;

public interface ICSVManager {
    void createFileIfMissing();

    HashMap<Long, Task> loadTasks();

    void saveTasks(HashMap<Long, Task> tasks);
}
