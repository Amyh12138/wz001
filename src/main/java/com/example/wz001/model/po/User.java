package com.example.wz001.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName(value = "user") //实体类与数据库类型的映射
@Data //注在类上，提供类的get、set、equals、hashCode、toString等方法
public class User {
    @TableId(type = IdType.AUTO)//专门用在主键上的注解,IdType.AUTO根据当前表中id最大值自增+1
    private Long id;
    private String userName;
    private String userPassword;
    private String userAccount;
    private String userAvatar;
    private Integer gender;
    private String userRole;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private String checkPassword;
    /*
    TableLogic逻辑删除，并不会物理删除数据效果：在字段上加上这个注解再执行BaseMapper的删除方法时，删除方法会变成修改
    @TableLogic(value=“原值”,delval=“修改值”)
    注解参数
　　　　    value = “” 未删除的值，默认值为0
　　　　    delval = “” 删除后的值，默认值为1
     */
   @TableLogic
    private Integer isDelete;

   @TableField(exist = false)// 实体类中的属性字段在表中不存在的问题
   private static final long serialVersionUID = 1L;



}
