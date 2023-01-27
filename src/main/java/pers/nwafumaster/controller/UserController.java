package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.beans.Question;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.service.UserService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.UserRegister;

import javax.annotation.Resource;

/**
 * @author Windlinxy
 * @description 用户行为相关
 * @date 2023-01-25 15:28
 **/
@RestController
@RequestMapping(value = "/user",
        produces = "application/json"
)
@Slf4j
public class UserController {
    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public JsonResult<User> login(@RequestBody User user) {
        log.info("/user/login : "+user);
        if ("".equals(user.getUsername()) || "".equals(user.getPassword())){
            return new JsonResult<User>().fail();
        }
        User userInData;
        if((userInData = userService.getOne(new QueryWrapper<>(user))) != null){
            return new JsonResult<User>().ok(userInData);
        }
        return new JsonResult<User>().fail();
    }

    @PostMapping("/register")
    public JsonResult<User> register(@RequestBody UserRegister userRegister) {
        User user =  new User(userRegister);
        log.info("/user/register : "+user);
        if ("".equals(user.getUsername()) || "".equals(user.getPassword())){
            return new JsonResult<User>().fail();
        }
        if(userService.save(user)){
            return new JsonResult<User>().ok(userService.getOne(new QueryWrapper<>(user)));
        }
        return new JsonResult<User>().fail();
    }


}
