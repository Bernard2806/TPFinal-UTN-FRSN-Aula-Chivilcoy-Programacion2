package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
// Se elimina java.io.FileReader (ya no se usa)
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import enums.TaskPriority;
import enums.TaskStatus;
import interfaces.ICSVManager;
import models.Task;

public class CSVManager implements ICSVManager {
    private String filePath;

    public CSVManager(String filePath) {
        this.filePath = filePath;
        // 1. Llamamos al nuevo método robusto DESDE EL CONSTRUCTOR
        ensureFileAndDirectoriesExist();
    }

    /**
     * Asegura que existan los directorios y el archivo CSV.
     * Si el archivo no existe, lo crea con el encabezado.
     */
    private void ensureFileAndDirectoriesExist() {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("El archivo no existe: " + filePath);
            try {
                // Escribir el encabezado usando UTF-8
                Files.write(path, "id,title,description,startDate,dueDate,status,priority\n".getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE_NEW); // CREATE_NEW es correcto aquí
                System.out.println("Archivo creado con encabezado.");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo:");
                e.printStackTrace(); // Usar printStackTrace
            }
        }
    }

    // Se eliminó el método createFileIfMissing() (reemplazado por el de arriba)

    public HashMap<Long, Task> loadTasks() {
        // 5. Se elimina la llamada a createFileIfMissing()
        HashMap<Long, Task> tasks = new HashMap<>();
        Path path = Paths.get(filePath);
        
        // Verificación de seguridad: si el archivo aún no existe (p.ej. falló la creación)
        if (!Files.exists(path)) {
            System.err.println("No se puede cargar: El archivo no existe en " + path.toAbsolutePath());
            return tasks; // Devolver mapa vacío
        }

        // 6. Usar el lector NIO con codificación UTF-8
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            boolean firstLine = true; // 7. Corregido error tipográfico (fristLine)

            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length != 7) {
                    System.out.println("⚠️ Línea mal formada: " + line);
                    continue;
                }

                try {
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
                } catch (IllegalArgumentException e) {
                    // Más detalles en el error
                    System.out.println("❗ Error en formato de datos (fecha, enum, etc.) en línea: " + line);
                    System.err.println("Detalle: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("❗ Error desconocido al procesar línea: " + line);
                    e.printStackTrace(); 
                }
            }
        } catch (IOException e) {
            System.out.println("💥 Error al leer el archivo:");
            e.printStackTrace(); 
        }
        return tasks;
    }

    public void saveTasks(HashMap<Long, Task> tasks) {
        // newBufferedWriter sobrescribirá el archivo, lo cual es correcto para "guardar".
        // Ya sabemos que el directorio existe gracias al constructor.
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            writer.write("id,title,description,startDate,dueDate,status,priority");
            writer.newLine();

            for (Task task : tasks.values()) {
                writer.write(task.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("💥 Error al escribir en el archivo:");
            e.printStackTrace();
        }
    }
}