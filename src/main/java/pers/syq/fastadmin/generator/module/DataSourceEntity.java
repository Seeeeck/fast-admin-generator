package pers.syq.fastadmin.generator.module;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DataSourceEntity {
    @NotBlank
    private String host;
    @Min(1)
    @NotNull
    private Integer port;
    @NotBlank
    private String database;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
