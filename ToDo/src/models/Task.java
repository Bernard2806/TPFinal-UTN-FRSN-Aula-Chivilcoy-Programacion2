package models;

import java.time.LocalDate;

import enums.*;

public class Task {

    // ---- Task Entity Data ----
    private long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private TaskStatus status;
    private TaskPriority priority;

    // ---- Task Entity Constructor ----
    public Task(long id, String title, String description, LocalDate startDate, LocalDate dueDate,
            TaskStatus status, TaskPriority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    // ---- Task Entity Getters and Setters ----
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    // ---- Task Entity toString() ----

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("Título: ").append(this.getTitle()).append("\n");
        output.append("Descripción: ").append(this.getDescription()).append("\n");
        output.append("Inicio: ").append(this.getStartDate()).append("\n");
        output.append("Fin: ").append(this.getDueDate()).append("\n");
        output.append("Estado: ").append(this.getStatus().getDisplayName()).append("\n");
        output.append("Prioridad: ").append(this.getPriority().getDisplayName());

        return output.toString();
    }

    // ---- Task Entity toCSV() ----
    public String toCSV() {
        return id + "," + title + "," + description + "," + startDate
                + "," + dueDate + "," + status + "," + priority;
    }
}