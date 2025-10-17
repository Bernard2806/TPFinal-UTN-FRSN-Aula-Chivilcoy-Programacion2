package interfaces;

import models.Task;

public interface ITaskManager {
    void addTask(Task task);
    void removeTask(long id);
    Task getTask(long id);
}