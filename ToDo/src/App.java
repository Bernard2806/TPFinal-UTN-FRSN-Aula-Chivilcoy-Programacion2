import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        viewMenu();
    }

    private static void viewMenu() {
        int opcion = 0;
        do {
            System.out.println("Menú:");
            System.out.println("1) Crear tarea");
            System.out.println("2) Listar tareas");
            System.out.println("3) Editar tarea");
            System.out.println("4) Eliminar tarea");
            System.out.println("5) Reportes");
            System.out.println("6) Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();
            logicMenu(opcion);
        } while (opcion != 6);
    }

    private static void logicMenu(int opcion) {
        switch (opcion) {
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
            case 6:
                System.out.println("Salir");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }
    }
}
