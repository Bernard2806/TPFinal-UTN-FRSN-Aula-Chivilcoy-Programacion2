package interfaces;

import java.util.HashMap;

import models.Task;

public interface ITaskManager {
    void addTask(Task task);

    void removeTask(long id);

    Task getTask(long id);

    HashMap<Long, Task> getTasks();

    void saveTasks();
}