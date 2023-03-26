package com.example.wz001.core.builder;

import com.example.wz001.core.builder.sql.MySQLDialect;
import com.example.wz001.core.builder.sql.SQLDialect;
import com.example.wz001.core.builder.sql.SQLDialectFactory;
import com.example.wz001.core.schema.TableSchema;
import com.example.wz001.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SqlBuilder {

    private SQLDialect sqlDialect;
    public SqlBuilder(){
        this.sqlDialect = SQLDialectFactory.getDialect(MySQLDialect.class.getName());
    }
    public SqlBuilder(SQLDialect sqlDialect){
        this.sqlDialect = sqlDialect;
    }
    public String buildCreateTableSql(TableSchema tableSchema){
        String template = "%s\n"//表前注释
                +"create table if not exists %s\n"//表名
                +"(\n"
                +"%s\n"//字段信息
                +") %s:";//表后注释

        //构造表名
        String tableName = sqlDialect.wrapTableName(tableSchema.getTableName());
        String dbName = tableSchema.getDbName();
        if (StringUtils.isNoneBlank(dbName)){
            tableName = String.format("%s.%s",dbName,tableName);
        }
        

        //表前注释
        String tableComment = tableSchema.getTableComment();
        if (StringUtils.isBlank(tableComment)){
            tableComment = tableName;
        }

        String tableSuffixComment = String.format("comment '%s'", tableComment);

        //构建
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        List<String> collect = fieldList.stream().map(field -> buildCreateFieldSql(field)).collect(Collectors.toList());
        String fieldStr = StringUtils.join(collect, ",\n");


        return String.format(template, tableComment, tableName, fieldStr, tableSuffixComment);
    }


    public String buildCreateFieldSql(TableSchema.Field field) {
        AssertUtil.isTrue(ObjectUtils.isEmpty(field),"参数不能为空");
        String fieldName = sqlDialect.wrapFieldName(field.getFieldName());
        String fieldType = field.getFieldType();
        String comment = field.getComment();
        String defaultValue = field.getDefaultValue();
        String mockParams = field.getMockParams();
        String mockType = field.getMockType();
        String onUpdate = field.getOnUpdate();
        boolean autoIncrement = field.isAutoIncrement();
        boolean notNull = field.isNotNull();
        boolean primaryKey = field.isPrimaryKey();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(fieldName).append(" ").append(fieldType);
        if (StringUtils.isNoneBlank(defaultValue)){stringBuilder.append(" default ").append(defaultValue);}
        stringBuilder.append(" ").append((notNull ? "not null" : "null"));
        if (autoIncrement) {
            stringBuilder.append(" ").append("auto_increment");
        }
        // 附加条件
        if (StringUtils.isNotBlank(onUpdate)) {
            stringBuilder.append(" ").append("on update ").append(onUpdate);
        }
        // 注释
        if (StringUtils.isNotBlank(comment)) {
            stringBuilder.append(" ").append(String.format("comment '%s'", comment));
        }
        // 是否为主键
        if (primaryKey) {
            stringBuilder.append(" ").append("primary key");
        }
        return stringBuilder.toString();

    }


}
