package enums;

public enum TaskStatus {
    PENDING("Pendiente", 1),
    IN_PROGRESS("En progreso", 2),
    COMPLETED("Completado", 3);

    private final String displayName;
    private final int numericValue;

    TaskStatus(String displayName, int numericValue) {
        this.displayName = displayName;
        this.numericValue = numericValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public static TaskStatus fromNumericValue(int value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getNumericValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("No TaskStatus with numeric value: " + value);
    }
}