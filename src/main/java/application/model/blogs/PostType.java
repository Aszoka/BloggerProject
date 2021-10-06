package application.model.blogs;

import lombok.Getter;

public enum PostType {
    NORMAL("normal"),
    SHAKESPEAREAN("shakespearean"),
    DICKENS("dickens");

    @Getter
    final String label;

    private PostType(String label) {
        this.label = label;
    }
}
