package pers.syq.fastadmin.generator.config;

import org.apache.velocity.app.Velocity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class VelocityInitRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
    }
}
