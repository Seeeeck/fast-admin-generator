package pers.syq.fastadmin.generator.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import pers.syq.fastadmin.generator.enums.ErrorCode;
import pers.syq.fastadmin.generator.exception.FastAdminException;
import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.module.GlobalConfigEntity;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Generator template for common files
 */
public abstract class AbstractCommonTemplate implements pers.syq.fastadmin.generator.template.Template {
    @Override
    public void generateCode(Map<String, Object> objectMap, ZipOutputStream zip) {
        VelocityContext velocityContext = createVelocityContext(objectMap);
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(absoluteTemplateFilePath(), "UTF-8");
        tpl.merge(velocityContext, sw);
        String fileName = absoluteOutputFilePath("");
        write2Zip(zip, sw, fileName);
    }

    @Override
    public String ConcatOutputFilePath(String path) {
        if (GeneratorData.globalConfig == null) {
            throw new FastAdminException(ErrorCode.NULL_GLOBAL_CONFIG);
        }
        GlobalConfigEntity globalConfig = GeneratorData.globalConfig;
        return path + "java" + File.separator + globalConfig.getPkg().replace(".", File.separator) +
                File.separator + "common" + File.separator;
    }
}
