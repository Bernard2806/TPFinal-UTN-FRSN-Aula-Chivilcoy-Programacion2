package enums;

public enum TaskPriority {
    HIGH("Alta", 3),
    MEDIUM("Media", 2),
    LOW("Baja", 1);

    private final String displayName;
    private final int numericValue;

    TaskPriority(String displayName, int numericValue) {
        this.displayName = displayName;
        this.numericValue = numericValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public static TaskPriority fromNumericValue(int value) {
        for (TaskPriority priority : TaskPriority.values()) {
            if (priority.getNumericValue() == value) {
                return priority;
            }
        }
        throw new IllegalArgumentException("No TaskPriority with numeric value: " + value);
    }
}