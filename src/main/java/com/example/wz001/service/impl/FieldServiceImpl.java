package com.example.wz001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wz001.mapper.FieldInfoMapper;
import com.example.wz001.model.po.FieldInfo;
import com.example.wz001.service.FieldInfoService;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImpl extends ServiceImpl<FieldInfoMapper, FieldInfo> implements FieldInfoService {

    @Override
    public void validAndHandleFieldInfo(FieldInfo fieldInfo, boolean add) {

    }
}
