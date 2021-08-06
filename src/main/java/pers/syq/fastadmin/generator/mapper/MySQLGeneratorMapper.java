package pers.syq.fastadmin.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.syq.fastadmin.generator.entity.ColumnEntity;
import pers.syq.fastadmin.generator.entity.TableEntity;

import java.util.List;

@Mapper
public interface MySQLGeneratorMapper  extends BaseMapper<TableEntity> {

    List<TableEntity> selectTableList();

    TableEntity selectTableByTableName(@Param("tableName") String tableName);

    List<ColumnEntity> selectColumnListByTableName(@Param("tableName") String tableName);
}
