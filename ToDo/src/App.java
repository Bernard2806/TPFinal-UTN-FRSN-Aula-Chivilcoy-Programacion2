import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static final int EXIT_OPTION = 6;

    public static void main(String[] args) throws Exception {
        viewMenu();
        scanner.close();
    }

    private static void viewMenu() {
        int option = 0;
        do {
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
        switch (option) {
            case 1:
                System.out.println("Crear tarea");
                break;
            case 2:
                System.out.println("Listar tareas");
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
    }
}
