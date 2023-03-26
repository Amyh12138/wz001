package com.example.wz001.coontroller;

import com.example.wz001.common.BaseResponse;
import com.example.wz001.common.ResultUtils;
import com.example.wz001.core.builder.SqlBuilder;
import com.example.wz001.core.schema.TableSchema;
import com.example.wz001.model.po.TableInfo;
import com.example.wz001.service.TableInfoService;
import com.example.wz001.util.AssertUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.wz001.common.BaseConstens.NON_NEGATIVE_ID_PARAMETER_ERROR;


@RestController
@RequestMapping("/table_info")
public class TableInfoController {

    @Autowired
    private TableInfoService tableInfoService;
    private final static Gson GSON = new Gson();


    @PostMapping("/generate/sql")
    public BaseResponse<String> generateCreateSql(@RequestBody long id){
        AssertUtil.isTrue(id < 0, NON_NEGATIVE_ID_PARAMETER_ERROR);
        TableInfo  tableInfo = tableInfoService.getById(id);
        AssertUtil.isTrue(tableInfo ==null,"获取对象为空");
        TableSchema tableSchema = GSON.fromJson(tableInfo.getContent(), TableSchema.class);
        SqlBuilder sqlBuilder = new SqlBuilder();

        return ResultUtils.success(sqlBuilder.buildCreateTableSql(tableSchema));

//        return sqlBuilder.buildCreateTableSql(tableSchema);
    }

    public static void main(String[] args) {
        String aaaa ="11111";
        String bbbb ="11111";
        System.out.println(aaaa);
        System.out.println(bbbb);
    }

}
