package pers.syq.fastadmin.generator.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ColumnFillVo {
    @NotBlank
    private String columnName;
    @NotNull
    private Integer fillCode;
}
