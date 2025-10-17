package services;

import java.util.HashMap;

import interfaces.*;
import models.Task;

public class TaskManager implements ITaskManager {

    // ---- Task Manager Data ----
    HashMap<Long, Task> tasks;
    long nextId;
    String fileSaveUri;

    // ---- Task Manager Constructor ----
    public TaskManager(String fileSaveUri) {
        tasks = new HashMap<Long, Task>();
        nextId = 0;
        this.fileSaveUri = fileSaveUri;
    }

    // ---- Task Manager Public Methods ----

    public void addTask(Task task) {
        tasks.put(nextId, task);
        nextId++;
    }

    public void removeTask(long id) {
        tasks.remove(id);
    }

    public Task getTask(long id) {
        return tasks.get(id);
    }
}
