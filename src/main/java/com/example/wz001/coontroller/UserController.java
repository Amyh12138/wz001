package com.example.wz001.coontroller;

import com.example.wz001.exception.ParamsExceptions;
import com.example.wz001.model.po.User;
import com.example.wz001.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody(required = false) User user){
        Map<String,Object> map = new HashMap<>();

//        AssertUtil.isTrue(StringUtils.isAnyBlank(user.getUserName(),user.getUserAccount(),user.getUserPassword(),user.getCheckPassword()),"参数不能为空");
        try{
            long n = userService.userRegister(user.getUserName(),user.getUserAccount(),user.getUserPassword(),user.getCheckPassword(),user.getUserRole());
            if (n > 0){
                map.put("code",200);
                map.put("msg","用户注册成功");
            }
        }catch (ParamsExceptions p){
            map.put("code",p.getCode());
            map.put("msg",p.getMsg());
        }catch (Exception e){
            map.put("code",500);
            map.put("msg","添加用户失败");
            e.printStackTrace();
        }
        return map;
    };

    @GetMapping("/getUser")
    public List<User> getUserByName() {
        return userService.getUser();
    }


}
