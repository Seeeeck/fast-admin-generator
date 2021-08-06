package pers.syq.fastadmin.generator.vo;

import lombok.Data;
import pers.syq.fastadmin.generator.module.GlobalConfigEntity;

import javax.validation.Valid;
import java.util.List;

@Data
public class GeneratorVo {
    private GlobalConfigEntity globalConfig;
    @Valid
    private List<TableInfoVo> tableInfos;
}
