package application.model.template;

import lombok.Getter;

public class Template {

    @Getter
    private String templateID;
    @Getter
    private String category;
    @Getter
    private String colorTheme;
    @Getter
    private byte[] background_picture;

    public Template() {
    }

    public Template(String templateID, String category, String colorTheme) {
        this.templateID = templateID;
        this.category = category;
        this.colorTheme = colorTheme;
    }
}
