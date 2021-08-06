package pers.syq.fastadmin.generator.module;

import lombok.Data;


@Data
public class GlobalConfigEntity {
    private String pkg;
    private String moduleName;
    private String tablePrefix;
    private boolean enableSwagger;
    private boolean enableLombok;
    private boolean hasFillField;
    private String author;
    private String email;

    public GlobalConfigEntity() {
        this.pkg = "com.example";
        this.moduleName = "module";
        this.enableSwagger = false;
        this.enableLombok = true;
        this.hasFillField = false;
        this.author = "author";
        this.email = "example@email.com";
    }
}
