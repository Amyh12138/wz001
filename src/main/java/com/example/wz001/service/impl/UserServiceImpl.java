package com.example.wz001.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wz001.common.ErrorCode;
import com.example.wz001.exception.ParamsExceptions;
import com.example.wz001.mapper.UserMapper;
import com.example.wz001.model.po.User;
import com.example.wz001.service.UserService;
import com.example.wz001.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yupi";



    @Override
    @Transactional(rollbackFor = Exception.class)
    public long userRegister(String userName, String userAccount, String userPassword, String checkPassword, String userRole) {
        AssertUtil.isTrue(StringUtils.isAnyBlank(userName, userAccount, userPassword, checkPassword), "参数不能为空");
        if (userName.length() > 15) {
            throw new ParamsExceptions("用户名过长");
        }
        if (userAccount.length() > 20 && userAccount.length() < 5) {
            throw new ParamsExceptions(ErrorCode.PARAMS_ERROR,"用户账号不能小于5位或大于20位");
        }
        if (userPassword.length() < 5) {
            throw new ParamsExceptions(ErrorCode.PARAMS_ERROR,"用户密码不能小于5位");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new ParamsExceptions(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()){
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userAccount",userAccount);
            long count = userMapper.selectCount(userQueryWrapper);
            if (count > 0){
                throw new ParamsExceptions(ErrorCode.PARAMS_ERROR, "用户名重复");


            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            User user = new User();
            user.setUserName(userName);
            user.setUserAccount(userAccount);
            user.setUserPassword(userPassword);
            user.setUserRole(userRole);
            boolean save = this.save(user);
            if (!save) {
                throw new ParamsExceptions(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public List<User> getUser() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select();
        List<User> u = userMapper.selectList(userQueryWrapper);

        return u;
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        return null;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        return false;
    }

}
