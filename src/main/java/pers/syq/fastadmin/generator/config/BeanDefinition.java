package pers.syq.fastadmin.generator.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.syq.fastadmin.generator.service.SpringService;


import javax.sql.DataSource;


@Configuration
public class BeanDefinition {

    @Bean
    public DataSource dataSource(SpringService springService) {
        return new DataSourceProxy(springService);
    }


}
