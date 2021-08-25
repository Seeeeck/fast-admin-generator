package pers.syq.fastadmin.generator.config;

import cn.hutool.core.util.RuntimeUtil;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@Component
public class InitRunner implements ApplicationRunner {
    @Value("${server.port}")
    private Integer port;

    @Value("${os.name}")
    private String os;


    @Override
    public void run(ApplicationArguments args) {
        initVelocity();
        openBrowse();
    }

    private void initVelocity(){
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
    }

    private void openBrowse(){
        String url = "http://localhost:"+port;
        try {
            if (os.contains("Mac")){
                //RuntimeUtil.exec("open " + url);
            }else if (os.contains("Windows")){
                RuntimeUtil.exec("cmd /c start " + url);
            }
        }catch (Exception e){
            //ignored
        }
    }
}
