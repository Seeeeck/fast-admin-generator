package pers.syq.fastadmin.generator.template;

import cn.hutool.http.HttpStatus;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import pers.syq.fastadmin.generator.exception.FastAdminException;

import java.io.StringWriter;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public abstract class AbstractVueMultipleTemplate implements Template{

    public void generateCode(Map<String, Object> objectMap, ZipOutputStream zip) {
        VelocityContext velocityContext = createVelocityContext(objectMap);
        Object frontPathName = velocityContext.get("frontPathName");
        if (frontPathName == null) {
            throw new FastAdminException("Unknown error", HttpStatus.HTTP_INTERNAL_ERROR);
        }
        StringWriter sw = new StringWriter();
        org.apache.velocity.Template tpl = Velocity.getTemplate(absoluteTemplateFilePath(), "UTF-8");
        tpl.merge(velocityContext, sw);
        String fileName = absoluteOutputFilePath((String) frontPathName);
        write2Zip(zip, sw, fileName);
    }

    @Override
    public String ConcatOutputFilePath(String path) {
        return null;
    }

    @Override
    public boolean toResources() {
        return true;
    }


}
