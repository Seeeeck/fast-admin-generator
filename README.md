# Fast Admin Generator

## What does it do

To generate the code for CRUD.

## frameworks

**Require Java frameworks**: Spring Boot,Mybatis plus

**Option frameworks**: Lombok,Swagger

## Quick Start

### Step 1:Run the application and access http://localhost:8000/

<img src="https://github.com/Seeeeck/fast-admin-generator/blob/master/imgs/step1.png" alt="step1" width="800" />

### Step 2:Set the datasource and configuration about tables.

<img src="https://github.com/Seeeeck/fast-admin-generator/blob/master/imgs/step2-1.png" alt="step2" width="800" />

<img src="https://github.com/Seeeeck/fast-admin-generator/blob/master/imgs/step2-2.png" alt="step2" width="800" />

### Step 3:Generate the code.

<img src="https://github.com/Seeeeck/fast-admin-generator/blob/master/imgs/step3-1.png" alt="step3" width="800" />

<img src="https://github.com/Seeeeck/fast-admin-generator/blob/master/imgs/step3-2.png" alt="step3" width="800" />

##　Customize

If you want to generate your own code,you can write  a class to extends <a href="https://github.com/Seeeeck/fast-admin-generator/blob/master/src/main/java/pers/syq/fastadmin/generator/template/AbstractTemplate.java">AbstractTemplate</a> or <a href="https://github.com/Seeeeck/fast-admin-generator/blob/master/src/main/java/pers/syq/fastadmin/generator/template/AbstractCommonTemplate.java">AbstractCommonTemplate</a> ,and write a template that matches it.

Like this:

```java
@Component //required
public class ServiceImplTemplate extends AbstractTemplate{
    @Override
    protected boolean toResources() {
        return false;
    }

    @Override
    protected String outputFilePath() {
        return "service" + File.separator + "impl";
    }

    @Override
    protected String templateFileName() {
        return "serviceImpl.java.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
```

### Template

#### ServiceImpl.java.vm

```java
package ${pkg}.${moduleName}.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${pkg}.common.utils.PageUtils;
import ${pkg}.${moduleName}.mapper.${className}Mapper;
import ${pkg}.${moduleName}.entity.${className}Entity;
import ${pkg}.${moduleName}.service.${className}Service;


@Service("${classname}Service")
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}Entity> implements ${className}Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<${className}Entity> page = this.page(getPage(params), new QueryWrapper<${className}Entity>());
        return new PageUtils(page);
    }

    private IPage<${className}Entity> getPage(Map<String, Object> params){
        long curPage = 1;
        long size = 10;
        if(params.get("page") != null){
            curPage = Long.parseLong((String)params.get("page"));
        }
        if(params.get("size") != null){
            size = Long.parseLong((String)params.get("size"));
        }
        return Page<${className}Entity> page = new Page<>(curPage, size);
    }
}
```



### What properties you can use in the template

Variables of <a href="https://github.com/Seeeeck/fast-admin-generator/blob/master/src/main/java/pers/syq/fastadmin/generator/entity/TableContext.java">TableContext</a> and <a href="https://github.com/Seeeeck/fast-admin-generator/blob/master/src/main/java/pers/syq/fastadmin/generator/module/GlobalConfigEntity.java">GlobalConfigEntity</a>.
