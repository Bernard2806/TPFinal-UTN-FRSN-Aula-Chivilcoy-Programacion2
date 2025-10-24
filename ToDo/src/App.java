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
                break;
            case 3:
                System.out.println("Editar tarea");
                break;
            case 4:
                System.out.println("Eliminar tarea");
                break;
            case 5:
                System.out.println("Reportes");
                break;
            case EXIT_OPTION:
                System.out.println("Salir");
                break;
            default:
                System.out.println("Error: Opción inválida");
                break;
        }
        scanner.nextLine();
        ConsoleUtils.clearConsole();
    }

    private static void optionOne(){
        System.out.println("---- Crear tarea ----");
        
        Task newTask = TaskInputHelper.createNewTaskFromConsole(scanner);

        taskManager.addTask(newTask);
        System.out.println("Tarea creada con éxito!");
        System.out.println(newTask);

    }

    private static void optionTwo(){
        System.out.println("---- Listar tareas ----");
    }
}
