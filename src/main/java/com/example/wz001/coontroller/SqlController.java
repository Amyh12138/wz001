package com.example.wz001.coontroller;

import com.example.wz001.common.BaseResponse;
import com.example.wz001.common.ResultUtils;
import com.example.wz001.core.schema.TableSchema;
import com.example.wz001.core.schema.TableSchemaBuilder;
import com.example.wz001.model.dto.GenerateByAutoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sql")
@Slf4j
public class SqlController {

    @PostMapping("get/schema/auto")
    public BaseResponse<TableSchema> getSchemaByAuto(@RequestBody GenerateByAutoRequest autoRequest){


        return ResultUtils.success(TableSchemaBuilder.buildFromAuto(autoRequest.getContent()));
    };
}
