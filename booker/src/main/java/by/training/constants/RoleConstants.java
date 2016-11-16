package by.training.constants;

public enum RoleConstants {

    ROLE_USER("USER");

    private String role;

    private RoleConstants(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

}
