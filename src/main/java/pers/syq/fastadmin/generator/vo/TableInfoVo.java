package pers.syq.fastadmin.generator.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TableInfoVo {
    @NotBlank
    private String tableName;
    @Valid
    private List<ColumnFillVo> columnFillVos;
}
