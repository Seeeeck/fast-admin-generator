package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class MapperTemplate extends AbstractTemplate{
    @Override
    boolean toResources() {
        return false;
    }

    @Override
    String outputFilePath() {
        return "mapper";
    }

    @Override
    String templateFileName() {
        return "Mapper.java.vm";
    }

    @Override
    Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
