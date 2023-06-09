package com.example.wz001.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wz001.model.po.FieldInfo;

public interface FieldInfoService extends IService<FieldInfo> {

    /**
     * 校验并处理
     *
     * @param fieldInfo
     * @param add 是否为创建校验
     */
    void validAndHandleFieldInfo(FieldInfo fieldInfo, boolean add);
}
