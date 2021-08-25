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
    // 0 中文,1 日语,2 英语
    @NotNull
    private Integer language;
    @NotBlank
    private String author;
    @NotNull
    private Boolean enableWebSecurity;

    @NotBlank
    @Email
    private String email;

}
