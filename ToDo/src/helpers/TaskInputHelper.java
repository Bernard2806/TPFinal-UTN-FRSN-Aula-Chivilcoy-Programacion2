package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import enums.TaskPriority;
import enums.TaskStatus;
import models.Task;

public class TaskInputHelper {

    // Define el formato de fecha argentino (dd/MM/yyyy)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    /**
     * Método principal que solicita todos los datos para crear una nueva Tarea.
     */
    public static Task createNewTaskFromConsole(Scanner scanner) {
        String title = promptForString(scanner, "Ingrese el título:");
        String description = promptForString(scanner, "Ingrese la descripción:");

        LocalDate startDate = promptForDate(scanner, "Ingrese la fecha de inicio (dd/MM/yyyy):");
        LocalDate dueDate = promptForDueDate(scanner, "Ingrese la fecha de fin (dd/MM/yyyy):", startDate);

        TaskStatus status = promptForTaskStatus(scanner, "Seleccione el estado:");
        TaskPriority priority = promptForTaskPriority(scanner, "Seleccione la prioridad:");

        // Asumimos ID 0, que luego será gestionado por otra capa (servicio).
        return new Task(0, title, description, startDate, dueDate, status, priority);
    }

    /**
     * Solicita un String no vacío. (Sin cambios)
     */
    public static String promptForString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: El valor no puede estar vacío. Intente de nuevo.");
            } else {
                return input;
            }
        }
    }

    /**
     * Solicita una fecha en formato dd/MM/yyyy. (Sin cambios)
     */
    public static LocalDate promptForDate(Scanner scanner, String prompt) {
        // Obtenemos la fecha de hoy una sola vez, antes de empezar el bucle
        LocalDate today = LocalDate.now();

        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                // 1. Intenta parsear la fecha
                LocalDate parsedDate = LocalDate.parse(input, DATE_FORMATTER);

                // 2. NUEVA VALIDACIÓN: Comprueba si la fecha es anterior a hoy
                if (parsedDate.isBefore(today)) {
                    System.out.println(
                            "Error: La fecha no puede ser en el pasado. Ingrese la fecha de hoy o una futura. Intente de nuevo.");
                    continue; // Vuelve al inicio del bucle sin retornar nada
                }

                // 3. Si ambas validaciones pasan, retorna la fecha
                return parsedDate;

            } catch (DateTimeParseException e) {
                // Captura solo el error de formato
                System.out.println("Error: Formato de fecha incorrecto. Use dd/MM/yyyy. Intente de nuevo.");
            }
        }
    }

    /**
     * Solicita la fecha de fin (dueDate), validando que sea >= startDate. (Sin
     * cambios)
     */
    public static LocalDate promptForDueDate(Scanner scanner, String prompt, LocalDate startDate) {
        LocalDate dueDate;
        while (true) {
            dueDate = promptForDate(scanner, prompt); // Reutiliza el validador de fecha
            if (dueDate.isBefore(startDate)) {
                System.out.println("Error: La fecha de fin no puede ser anterior a la fecha de inicio ("
                        + startDate.format(DATE_FORMATTER) + "). Intente de nuevo.");
            } else {
                return dueDate;
            }
        }
    }

    /**
     * Helper simple para pedir un ID (long). (Sin cambios)
     */
    public static long promptForTaskId(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido. Intente de nuevo.");
            }
        }
    }

    // --- NUEVOS MÉTODOS PARA ENUMS ESPECÍFICOS ---

    /**
     * Solicita un TaskStatus, mostrando el displayName y validando con
     * numericValue.
     */
    public static TaskStatus promptForTaskStatus(Scanner scanner, String prompt) {
        System.out.println(prompt);

        // Muestra las opciones usando los métodos del enum
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println(status.getNumericValue() + ". " + status.getDisplayName());
        }
        System.out.println("Ingrese el número de la opción:");

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                // Usa el método estático del enum for la validación
                return TaskStatus.fromNumericValue(choice);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            } catch (IllegalArgumentException e) {
                // Captura la excepción de fromNumericValue si el número no es válido
                System.out.println("Error: Opción no válida. Intente de nuevo.");
            }
        }
    }

    public static void editTaskFromConsole(Scanner scanner, Task task) {
        int option;
        do {
            System.out.println("---- Editar Tarea ----");
            System.out.println(task);
            System.out.println("----------------");
            System.out.println("¿Qué desea editar?");
            System.out.println("1. Título");
            System.out.println("2. Descripción");
            System.out.println("3. Fecha de inicio");
            System.out.println("4. Fecha de fin");
            System.out.println("5. Estado");
            System.out.println("6. Prioridad");
            System.out.println("7. Salir");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    task.setTitle(promptForString(scanner, "Ingrese el nuevo título:"));
                    break;
                case 2:
                    task.setDescription(promptForString(scanner, "Ingrese la nueva descripción:"));
                    break;
                case 3:
                    task.setStartDate(promptForDate(scanner, "Ingrese la nueva fecha de inicio (dd/MM/yyyy):"));
                    break;
                case 4:
                    task.setDueDate(promptForDueDate(scanner, "Ingrese la nueva fecha de fin (dd/MM/yyyy):", task.getStartDate()));
                    break;
                case 5:
                    task.setStatus(promptForTaskStatus(scanner, "Seleccione el nuevo estado:"));
                    break;
                case 6:
                    task.setPriority(promptForTaskPriority(scanner, "Seleccione la nueva prioridad:"));
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (option != 7);
    }

    /**
     * Solicita un TaskPriority, mostrando el displayName y validando con
     * numericValue.
     */
    public static TaskPriority promptForTaskPriority(Scanner scanner, String prompt) {
        System.out.println(prompt);

        // Muestra las opciones usando los métodos del enum
        for (TaskPriority priority : TaskPriority.values()) {
            System.out.println(priority.getNumericValue() + ". " + priority.getDisplayName());
        }
        System.out.println("Ingrese el número de la opción:");

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                // Usa el método estático del enum for la validación
                return TaskPriority.fromNumericValue(choice);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            } catch (IllegalArgumentException e) {
                // Captura la excepción de fromNumericValue si el número no es válido
                System.out.println("Error: Opción no válida. Intente de nuevo.");
            }
        }
    }
}
