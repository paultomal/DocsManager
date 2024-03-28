package com.example.document_management_tool.enums;

public enum Priority {

    USUAL("Usual"),
    URGENT("Urgent");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public static Priority getPriorityByLabel(String label) {
        for (Priority priority : Priority.values()) {
            if (priority.label.equals(label)) return priority;
        }
        return null;
    }
    public static Priority getPriorityFromString(String priorityString) {
        for (Priority priority : Priority.values()) {
            if (priority.toString().equalsIgnoreCase(priorityString)) {
                return priority;
            }
        }
        return null;
    }

    public static String getLabelByPriority(Priority priority) {
        return priority.label;
    }

}
