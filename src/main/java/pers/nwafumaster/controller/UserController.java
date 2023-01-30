package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.annotation.PassToken;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.config.JwtConfig;
import pers.nwafumaster.service.DiseaseService;
import pers.nwafumaster.service.UserService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.MyPage;
import pers.nwafumaster.vo.UserRegister;

import javax.annotation.Resource;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Windlinxy
 * @description 用户行为相关
 * @date 2023-01-25 15:28
 **/
@RestController
@RequestMapping(
        produces = "application/json"
)
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private DiseaseService diseaseService;

    @Resource
    private JwtConfig jwtConfig;



    @PostMapping("/user/login")
    @PassToken
    public JsonResult<Object> login(@RequestBody User user) {
        log.info("/user/login : " + user);
        if (!StringUtils.hasLength(user.getUsername()) || !StringUtils.hasLength(user.getPassword())) {
            return new JsonResult<>().fail();
        }
        User userInData;
        if ((userInData = userService.getOne(new QueryWrapper<>(user))) != null) {
            String token = jwtConfig.sign(userInData);
            Map<String, Object> body = new HashMap<>();
            body.put("accessToken", token);
            body.put("username",userInData.getUsername());
            body.put("userId", userInData.getUserId());
            return new JsonResult<>().ok(body);
        }
        return new JsonResult<>().fail();
    }

    @PostMapping("/user/check")
    @PassToken
    public JsonResult<Object> duplicateCheck(String username) {
        log.info("/user/check: {}", username);
        if (!StringUtils.hasLength(username)) {
            return new JsonResult<>().fail("用户名为空");
        }
        long result = userService.count(new QueryWrapper<User>().select("username").eq("username", username));
        if (result == 1) {
            return new JsonResult<>().fail("用户名已存在");
        }
        return new JsonResult<>().ok();
    }


    @PostMapping("/user/register")
    @PassToken
    public JsonResult<User> register(@RequestBody UserRegister userRegister) {
        User user = new User(userRegister);
        log.info("/user/register : " + userRegister);
        if (!StringUtils.hasLength(user.getUsername()) || !StringUtils.hasLength(user.getPassword())) {
            return new JsonResult<User>().fail();
        }
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(User::getUsername, user.getUsername())
                .select(User::getUsername);
        long result = userService.count(userQuery);
        if (result == 1) {
            return new JsonResult<User>().fail("用户名已存在");
        }
        userService.save(user);
        return new JsonResult<User>().ok(userService.getOne(new QueryWrapper<>(user)));
    }

    @GetMapping("/disease")
    public JsonResult<MyPage<Disease>> diseaseList(
            @RequestParam("name") String diseaseName,
            @RequestParam("type") String diseaseType,
            @RequestParam("cur") int currentPage,
            @RequestParam("size") int pageSize) {
        MyPage<Disease> myPage = new MyPage<>(currentPage, pageSize);
        if (StringUtils.hasLength(diseaseType)) {
            return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, new QueryWrapper<Disease>().eq("disease_type", diseaseType)));
        }
        if (StringUtils.hasLength(diseaseName)){
            return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, new QueryWrapper<Disease>().like("disease_name", diseaseName)));
        }
        return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage));
    }


}
