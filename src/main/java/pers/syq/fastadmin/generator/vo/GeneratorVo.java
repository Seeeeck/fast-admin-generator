package pers.syq.fastadmin.generator.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GeneratorVo {

    @NotNull
    private Boolean singleton;

    @Valid
    private List<TableInfoVo> tableInfoVos;
}
