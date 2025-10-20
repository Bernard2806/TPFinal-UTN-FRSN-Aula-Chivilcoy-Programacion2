package helpers;

import java.io.IOException;

public class ConsoleUtils {
    /**
     * Limpia la pantalla de la consola ejecutando el comando nativo del SO.
     * ADVERTENCIA: Esto solo funciona si se ejecuta desde una terminal real
     * (cmd, PowerShell, bash, etc.). No funcionará en la consola
     * integrada de la mayoría de los IDEs (Eclipse, IntelliJ, etc.).
     */
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            ProcessBuilder processBuilder = new ProcessBuilder();

            if (os.contains("win")) {
                // Para Windows (cmd.exe o PowerShell)
                processBuilder.command("cmd", "/c", "cls");
            } else {
                // Para Unix-like (Linux, macOS)
                // Usamos "clear"
                processBuilder.command("clear");
            }

            // Redirige la salida del proceso a la salida estándar de Java
            processBuilder.inheritIO().start().waitFor();

        } catch (IOException | InterruptedException e) {
            // Maneja la excepción (por ejemplo, si "clear" no existe)
            // En un caso real, podrías querer loggear esto.
            System.err.println("Error al intentar limpiar la consola: " + e.getMessage());

            // Si falla, intenta el método de escape ANSI como fallback
            clearConsoleANSI();
        }
    }

    /**
     * Método alternativo que usa secuencias de escape ANSI.
     * Funciona en terminales modernas (Linux, macOS, Windows Terminal)
     * pero tampoco funciona en IDEs clásicos.
     */
    public static void clearConsoleANSI() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
