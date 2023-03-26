package com.example.wz001.core.builder.sql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SQLDialectFactory {
    private static final Map<String,SQLDialect> DIALECT_POOR= new ConcurrentHashMap<>();

    private SQLDialectFactory(){

    }
    public static SQLDialect getDialect(String className){
        SQLDialect sqlDialect = DIALECT_POOR.get(className);

        if (sqlDialect == null){
            synchronized (className.intern()){
                sqlDialect = DIALECT_POOR.computeIfAbsent(className,
                        key ->{try {
                            return (SQLDialect) Class.forName(className).newInstance();// TODO: 2023/3/18 ???????怎末替代

                        }catch (Exception e){
                            throw new RuntimeException("系统内部异常");
                        }
                        });
            }
        }

        return sqlDialect;

    }
}
