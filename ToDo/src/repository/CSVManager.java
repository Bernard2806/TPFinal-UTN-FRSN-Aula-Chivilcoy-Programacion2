package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import enums.TaskPriority;
import enums.TaskStatus;
import models.Task;

public class CSVManager {
    private String filePath;

    public CSVManager(String filePath) {
        this.filePath = filePath;
    }

    public void createFileIfMissing() {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("El archivo no existe: " + filePath);
            try {
                Files.write(path, "id,title,description,startDate,dueDate,status,priority\n".getBytes(),
                        StandardOpenOption.CREATE_NEW);
                System.out.println("Archivo creado con encabezado.");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e.getMessage());
            }
        }

        if (!Files.isReadable(path)) {
            System.out.println("No se puede leer el archivo (permisos insuficientes): " + filePath);
        }
    }

    public HashMap<Long, Task> loadTasks() {
        createFileIfMissing();
        HashMap<Long, Task> tasks = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean fristLine = true;

            while ((line = bufferedReader.readLine()) != null) {
                if (fristLine) {
                    fristLine = false;
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length != 7) {
                    System.out.println("⚠️ Línea mal formada: " + line);
                    continue;
                }

                try{
                    long id = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String description = parts[2];
                    LocalDate startDate = LocalDate.parse(parts[3]);
                    LocalDate dueDate = LocalDate.parse(parts[4]);
                    String statusString = parts[5];
                    String priorityString = parts[6];

                    TaskStatus status = Arrays.stream(TaskStatus.values())
                            .filter(s -> s.name().equalsIgnoreCase(statusString))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + statusString));
                    
                    TaskPriority priority = Arrays.stream(TaskPriority.values())
                            .filter(p -> p.name().equalsIgnoreCase(priorityString))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Prioridad inválida: " + priorityString));


                    Task task = new Task(id, title, description, startDate, dueDate, status, priority);
                    tasks.put(id, task);
                }
                catch(IllegalArgumentException e) {
                    System.out.println("❗ Error en formato fecha: " + line);
                } catch (Exception e) {
                    System.out.println("❗ Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("💥 Error al leer el archivo: " + e.getMessage());
        }
        return tasks;
    }
}
