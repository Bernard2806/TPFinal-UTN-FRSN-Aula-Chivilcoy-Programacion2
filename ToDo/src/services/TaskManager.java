package services;

import java.util.HashMap;

import interfaces.ITaskManager;
import interfaces.ICSVManager;
import repository.CSVManager;
import models.Task;

public class TaskManager implements ITaskManager {

    // ---- Task Manager Data ----
    private HashMap<Long, Task> tasks;
    private long nextId;
    private String fileSaveUri;

    // ---- Task Manager CSV Manager ----
    private ICSVManager csvManager;

    // ---- Task Manager Constructor ----
    public TaskManager(String fileSaveUri) {
        this.fileSaveUri = fileSaveUri;
        this.csvManager = new CSVManager(fileSaveUri);
        this.tasks = csvManager.loadTasks();
        nextId = getNextId();
    }

    // ---- Task Manager Public Methods ----

    public void addTask(Task task) {
        if (task.getId() == 0)
            task.setId(nextId);
        tasks.put(nextId, task);
        nextId++;
    }

    public void removeTask(long id) {
        tasks.remove(id);
    }

    public Task getTask(long id) {
        return tasks.get(id);
    }

    public HashMap<Long, Task> getTasks() {
        return tasks;
    }

    public void saveTasks() {
        csvManager.saveTasks(this.tasks);
    }

    // ---- Task Manager Private Methods ----

    public long getNextId() {
        return tasks.keySet().stream()
                .max(Long::compare)
                .orElse(0L) + 1;
    }
}