package pers.syq.fastadmin.generator.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.generator.enums.ErrorCode;
import pers.syq.fastadmin.generator.exception.FastAdminException;
import pers.syq.fastadmin.generator.module.GlobalConfigEntity;
import pers.syq.fastadmin.generator.utils.R;
import pers.syq.fastadmin.generator.entity.ColumnEntity;
import pers.syq.fastadmin.generator.module.DataSourceEntity;
import pers.syq.fastadmin.generator.entity.TableEntity;
import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.service.GeneratorService;
import pers.syq.fastadmin.generator.vo.GeneratorVo;
import pers.syq.fastadmin.generator.vo.TableInfoVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@Validated
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;


    @GetMapping("/tables")
    public R<?> listTables() {
        if (GeneratorData.dataSource == null) {
            return R.error().errorCode(ErrorCode.NULL_DATABASE);
        }
        List<TableEntity> tables = generatorService.listTables();
        return R.ok(tables);
    }
    

    @GetMapping("/tables/columns")
    public R<?> listColumnsByTableName(@RequestParam("tableName") @NotBlank String tableName) {
        if (GeneratorData.dataSource == null) {
            return R.error().errorCode(ErrorCode.NULL_DATABASE);
        }
        List<ColumnEntity> columnEntities = generatorService.listColumnsByTableName(tableName);
        return columnEntities.isEmpty() ? R.error().code(HttpStatus.HTTP_NOT_FOUND).msg("not found") : R.ok(columnEntities);
    }


    @PostMapping("/datasource")
    public R<?> injectDataSource(@RequestBody @Validated DataSourceEntity dataSourceEntity) {
        boolean success = generatorService.injectDataSource(dataSourceEntity);
        return success ? R.ok() : R.error();
    }

    @GetMapping("/datasource")
    public R<?> getDataSourceData() {
        return GeneratorData.dataSource == null ?
                R.error().code(HttpStatus.HTTP_NOT_FOUND).msg("not found") : R.ok(GeneratorData.dataSource);
    }

    @PostMapping("/config")
    public R<?> saveGlobalConfig(@RequestBody @Validated GlobalConfigEntity globalConfig) {
        GeneratorData.globalConfig = globalConfig;
        return R.ok();
    }

    @GetMapping("/config")
    public R<?> getGlobalConfigData() {
        return GeneratorData.globalConfig == null ?
                R.error().code(HttpStatus.HTTP_NOT_FOUND).msg("not found") : R.ok(GeneratorData.globalConfig);
    }


    @PostMapping("/generate")
    public void generateCode(@RequestBody @Validated GeneratorVo generatorVo, HttpServletResponse response) throws IOException {
        if (GeneratorData.dataSource == null) {
            throw new FastAdminException(ErrorCode.NULL_DATABASE);
        }
        if (GeneratorData.globalConfig == null) {
            throw new FastAdminException(ErrorCode.NULL_GLOBAL_CONFIG);
        }
        byte[] data = generatorService.generateCode(generatorVo);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"fastadmin.zip\"");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), false, data);
    }


}
