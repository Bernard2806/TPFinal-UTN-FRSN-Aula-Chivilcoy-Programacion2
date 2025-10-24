import java.util.Scanner;
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
