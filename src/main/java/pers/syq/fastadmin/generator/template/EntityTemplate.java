package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class EntityTemplate extends AbstractTemplate{


    @Override
    protected boolean toResources() {
        return false;
    }

    @Override
    protected String outputFilePath() {
        return "entity";
    }

    @Override
    protected String templateFileName() {
        return "Entity.java.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
