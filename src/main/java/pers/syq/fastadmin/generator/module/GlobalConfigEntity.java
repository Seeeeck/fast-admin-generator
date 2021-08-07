package pers.syq.fastadmin.generator.module;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class GlobalConfigEntity {
    @NotBlank
    private String pkg;
    @NotBlank
    private String moduleName;
    private String tablePrefix;
    @NotNull
    private Boolean enableSwagger;
    @NotNull
    private Boolean enableLombok;
    @NotBlank
    private String author;
    @NotBlank
    @Email
    private String email;

}
