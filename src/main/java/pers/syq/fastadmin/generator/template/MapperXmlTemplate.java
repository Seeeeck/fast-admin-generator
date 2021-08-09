package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class MapperXmlTemplate extends AbstractTemplate{
    @Override
    boolean toResources() {
        return true;
    }

    @Override
    String outputFilePath() {
        return "mapper";
    }

    @Override
    String templateFileName() {
        return "Mapper.xml.vm";
    }

    @Override
    Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
