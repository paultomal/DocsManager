package com.example.document_management_tool.enums;

public enum UserRoles {
    ROLE_ROOT("Root"),
    ROLE_SUPERVISOR("Supervisor"),
    ROLE_EMPLOYEE("Employee");
    private final String label;

    UserRoles(String label) {
        this.label = label;
    }

    public static UserRoles getUserRolesByLabel(String label) {
        for (UserRoles roles : UserRoles.values()) {
            if (roles.label.equals(label)) return roles;
        }
        return null;
    }

    public static String getLabelByUserRoles(UserRoles userRoles) {
        return userRoles.label;
    }
}

