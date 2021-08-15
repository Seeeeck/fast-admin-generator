package pers.syq.fastadmin.generator.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import pers.syq.fastadmin.generator.enums.ErrorCode;
import pers.syq.fastadmin.generator.exception.FastAdminException;
import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.module.GlobalConfigEntity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Generator template
 */
public abstract class AbstractMultipleTemplate implements pers.syq.fastadmin.generator.template.Template {


    @Override
    public String ConcatOutputFilePath(String path) {
        if (GeneratorData.globalConfig == null) {
            throw new FastAdminException(ErrorCode.NULL_GLOBAL_CONFIG);
        }
        GlobalConfigEntity globalConfig = GeneratorData.globalConfig;
        return path + "java" + File.separator + globalConfig.getPkg().replace(".", File.separator) +
                File.separator + globalConfig.getModuleName() + File.separator;
    }

    public void generateCode(Map<String, Object> objectMap, ZipOutputStream zip) {
        VelocityContext velocityContext = createVelocityContext(objectMap);
        Object className = velocityContext.get("className");
        if (className == null) {
            throw new FastAdminException("Unknown error", HttpStatus.HTTP_INTERNAL_ERROR);
        }
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(absoluteTemplateFilePath(), "UTF-8");
        tpl.merge(velocityContext, sw);
        String fileName = absoluteOutputFilePath((String) className);
        write2Zip(zip, sw, fileName);
    }


}
