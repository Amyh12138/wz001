package com.example.wz001.core.schema;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wz001.model.po.FieldInfo;
import com.example.wz001.service.FieldInfoService;
import com.example.wz001.util.AssertUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TableSchemaBuilder {
    private final static Gson GSON = new Gson();//创建类层级不可变的Gson(?)

    @Autowired
    private static FieldInfoService fieldInfoService;//创建类层级FieldInfoService(文件输入类型的服务层)
    private static final String[] DATE_PATTERNS = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    /**
     * 智能构建
     *
     * @param content
     * @return
     */
    public static TableSchema buildFromAuto(String content) {
        AssertUtil.isTrue(StringUtils.isEmpty(content),"参数异常,不能为空");

        String[] words = content.split("[,，]");
        AssertUtil.isTrue(ArrayUtils.isEmpty(words),"参数异常,不能为空");
        AssertUtil.isTrue(words.length >20,"参数异常,不能大于20");

        QueryWrapper<FieldInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("name", Arrays.asList(words)).or().in("fieldName",Arrays.asList(words));
        List<FieldInfo> fieldInfoList = fieldInfoService.list(queryWrapper);

        Map<String,List<FieldInfo>> nameFilterInForMap = fieldInfoList.stream().collect(Collectors.groupingBy(FieldInfo::getName));
        Map<String, List<FieldInfo>> fieldNameFieldInfoMap = fieldInfoList.stream().collect(Collectors.groupingBy(FieldInfo::getFieldName));
        TableSchema tableSchema = new TableSchema();//创建TableSchema对象
        tableSchema.setTableName("my_table");//命名
        tableSchema.setTableComment("自动生成的表");//调用set方法
        List<TableSchema.Field> fieldList = new ArrayList<>();

        for (String word:words) {
            TableSchema.Field field;
            List<FieldInfo> infoList = Optional.ofNullable(nameFilterInForMap.get(word)).orElse(fieldNameFieldInfoMap.get(word));
            if (CollectionUtils.isEmpty(infoList)){
                field = GSON.fromJson(infoList.get(0).getContent(), TableSchema.Field.class);
            }else {
                field = getDefaultField(word);
            }
            fieldList.add(field);
        }
        tableSchema.setFieldList(fieldList);

        return tableSchema;
    };







    /**
     * 获取默认字段
     *
     * @param word
     * @return
     */
    private static TableSchema.Field getDefaultField(String word) {
        final TableSchema.Field field = new TableSchema.Field();
        field.setFieldName(word);
        field.setFieldType("text");
        field.setDefaultValue("");
        field.setNotNull(false);
        field.setComment(word);
        field.setPrimaryKey(false);
        field.setAutoIncrement(false);
        field.setMockType("");
        field.setMockParams("");
        field.setOnUpdate("");
        return field;
    }






















}
