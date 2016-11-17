package by.training.common;

public enum Role {

    ROLE_USER("USER");

    private String role;

    private Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

}
