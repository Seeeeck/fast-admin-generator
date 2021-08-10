package pers.syq.fastadmin.generator.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Generator template for common files
 */
public abstract class AbstractCommonTemplate extends AbstractTemplate {
    @Override
    public void generateCode(Map<String,Object> objectMap, ZipOutputStream zip){
        VelocityContext velocityContext = createVelocityContext(objectMap);
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(absoluteTemplateFilePath(), "UTF-8");
        tpl.merge(velocityContext, sw);
        String fileName = absoluteOutputFilePath("");
        write2Zip(zip,sw,fileName);
    }

    @Override
    public boolean isCommon(){
        return true;
    }
}
