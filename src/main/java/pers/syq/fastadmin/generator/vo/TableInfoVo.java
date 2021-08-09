package pers.syq.fastadmin.generator.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TableInfoVo {
    @NotBlank
    private String tableName;

    @NotNull
    private Integer idTypeCode;

    @Valid
    private List<ColumnInfoVo> columnInfoVos;
}
