import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import helpers.ConsoleUtils;
import helpers.TaskInputHelper;
import interfaces.ITaskManager;
import models.Task;
import services.TaskManager;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static final int EXIT_OPTION = 6;
    private static final String FILE_SAVE_URI = "archives/tasks.csv";
    private static ITaskManager taskManager = new TaskManager(FILE_SAVE_URI);

    public static void main(String[] args) throws Exception {
        viewMenu();
        scanner.close();
    }

    private static void viewMenu() {
        int option = 0;
        do {
            ConsoleUtils.clearConsole();
            System.out.println("Menú:");
            System.out.println("1) Crear tarea");
            System.out.println("2) Listar tareas");
            System.out.println("3) Editar tarea");
            System.out.println("4) Eliminar tarea");
            System.out.println("5) Reportes");
            System.out.println("6) Salir");

            try {
                option = scanner.nextInt();
                scanner.nextLine();
                logicMenu(option);
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Por favor, ingrese una opción numérica válida.");
                scanner.nextLine();
            }
        } while (option != EXIT_OPTION);
    }

    private static void logicMenu(int option) {
        ConsoleUtils.clearConsole();
        switch (option) {
            case 1:
                optionOne();
                break;
            case 2:
                optionTwo();
                break;
            case 3:
                optionThree();
                break;
            case 4:
                optionFour();
                break;
            case 5:
                optionFive();
                break;
            case EXIT_OPTION:
                optionExit();
                break;
            default:
                System.out.println("Error: Opción inválida");
                break;
        }
        scanner.nextLine();
        ConsoleUtils.clearConsole();
    }

    private static void optionOne() {
        System.out.println("---- Crear tarea ----");

        Task newTask = TaskInputHelper.createNewTaskFromConsole(scanner);

        taskManager.addTask(newTask);

        ConsoleUtils.clearConsole();

        System.out.println("Tarea creada con éxito!");
        System.out.println(newTask);

    }

    private static void optionTwo() {
        System.out.println("---- Listar tareas ----");

        var tasks = taskManager.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
            return;
        }

        for (Task task : tasks.values()) {
            System.out.println(task);
            System.out.println("----------------");
        }
    }

    private static void optionThree() {
        System.out.println("---- Editar tarea ----");

        Task task = findTaskById();

        if (task == null) {
            System.out.println("No se encontró la tarea.");
            return;
        }

        TaskInputHelper.editTaskFromConsole(scanner, task);

        System.out.println("Tarea editada con éxito!");
    }

    private static void optionFour() {
        System.out.println("---- Eliminar tarea ----");

        optionTwo();

        System.out.println("Ingrese el ID de la tarea a eliminar:");
        long id = scanner.nextLong();
        scanner.nextLine();

        taskManager.removeTask(id);

        System.out.println("Tarea eliminada con éxito!");
    }

    private static void optionFive() {
        int option = 0;
        do {
            System.out.println("---- Reportes ----");
            System.out.println("1) Listar Todas las Tareas");
            System.out.println("2) Tareas por Estado");
            System.out.println("3) Tareas de la Semana Actual");
            System.out.println("4) Volver");

            try {
                option = scanner.nextInt();
                scanner.nextLine();
                logicReportsMenu(option);
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Por favor, ingrese una opción numérica válida.");
                scanner.nextLine();
            }
        } while (option != 4);
    }

    private static void logicReportsMenu(int option) {
        ConsoleUtils.clearConsole();
        switch (option) {
            case 1:
                optionTwo();
                break;
            case 2:
                listTasksByStatus();
                break;
            case 3:
                listTasksCurrentWeek();
                break;
            case 4:
                break;
            default:
                System.out.println("Error: Opción inválida");
                break;
        }
        scanner.nextLine();
    }

    private static void listTasksByStatus() {
        System.out.println("---- Tareas por Estado ----");

        Map<enums.TaskStatus, List<Task>> tasksByStatus = taskManager.getTasks().values().stream()
                .collect(Collectors.groupingBy(Task::getStatus));

        if (tasksByStatus.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
            return;
        }

        tasksByStatus.forEach((status, tasks) -> {
            System.out.println("Estado: " + status);
            tasks.forEach(task -> {
                System.out.println(task);
                System.out.println("----------------");
            });
        });
    }

    private static void listTasksCurrentWeek() {
        System.out.println("---- Tareas de la Semana Actual ----");

        LocalDate today = LocalDate.now();
        Locale locale = Locale.getDefault();
        TemporalField fieldISO = WeekFields.of(locale).dayOfWeek();
        LocalDate startOfWeek = today.with(fieldISO, 1); 
        LocalDate endOfWeek = startOfWeek.plusDays(6); 

        List<Task> tasksCurrentWeek = taskManager.getTasks().values().stream()
                .filter(task -> {
                    LocalDate dueDate = task.getDueDate();
                    return !dueDate.isBefore(startOfWeek) && !dueDate.isAfter(endOfWeek);
                })
                .collect(Collectors.toList());

        if (tasksCurrentWeek.isEmpty()) {
            System.out.println("No hay tareas para esta semana.");
            return;
        }

        tasksCurrentWeek.forEach(task -> {
            System.out.println(task);
            System.out.println("----------------");
        });
    }

    private static Task findTaskById() {
        System.out.println("---- Buscar tarea por ID ----");

        optionTwo();

        System.out.println("Ingrese el ID de la tarea:");
        long id = scanner.nextLong();
        scanner.nextLine();

        return taskManager.getTask(id);
    }

    private static void optionExit() {
        taskManager.saveTasks();
        System.out.println("Saliendo...");
        System.exit(0);
    }
}