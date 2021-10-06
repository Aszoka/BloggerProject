package application.model.users;

import lombok.Getter;

public enum Role {

    ADMIN("admin"),
    MODERATOR("moderator"),
    USER("user");

    @Getter
    final String label;

    private Role(String label) {
        this.label = label;
    }
}
