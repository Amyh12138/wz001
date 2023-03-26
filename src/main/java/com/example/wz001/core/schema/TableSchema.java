package com.example.wz001.core.schema;

import lombok.Data;

import java.util.List;

@Data
public class TableSchema {
    private String dbName;//库名
    private String tableName;//表名
    private String tableComment;//表注释
    private Integer mockNum;//模拟数据条数
    private List<Field> fieldList;//列信息列表

    @Data
    public static class Field{
        private String fieldName;//字段名
        private String fieldType;//字段类型
        private String defaultValue;//默认值
        private boolean notNull;//是否非空
        private String comment;//注释
        private boolean primaryKey;//是否为主键
        private boolean autoIncrement;//是否自增
        private String mockType;//模拟类型
        /**
         * 模拟参数
         */
        private String mockParams;
        private String onUpdate;//附加条件


    }
}
