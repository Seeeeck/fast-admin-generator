package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class EntityTemplate extends AbstractTemplate{
    @Override
    boolean toResources() {
        return false;
    }

    @Override
    String outputFilePath() {
        return "entity";
    }

    @Override
    String templateFileName() {
        return "Entity.java.vm";
    }

    @Override
    Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
