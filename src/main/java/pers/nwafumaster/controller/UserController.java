package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.annotation.PassToken;
import pers.nwafumaster.beans.*;
import pers.nwafumaster.config.JwtConfig;
import pers.nwafumaster.service.*;
import pers.nwafumaster.vo.*;

import javax.annotation.Resource;
import java.util.*;

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


    @Resource
    private UserEntityService userEntityService;

    @Resource
    private QuestionService questionService;


    /**
     * 用户注册
     *
     * @param user 用户名，密码
     * @return 响应
     */
    @PostMapping("/user/login")
    @PassToken
    public JsonResult<Object> login(@RequestBody User user) {
        log.info("/user/login : " + user.getUsername());
        if (!StringUtils.hasLength(user.getUsername()) || !StringUtils.hasLength(user.getPassword())) {
            return new JsonResult<>().fail();
        }
        User userInData;
        if ((userInData = userService.getOne(new QueryWrapper<>(user))) != null) {
            String token = jwtConfig.sign(userInData);
            Map<String, Object> body = new HashMap<>();
            body.put("accessToken", token);
            body.put("username", userInData.getUsername());
            body.put("userId", userInData.getUserId());
            return new JsonResult<>().ok(body);
        }
        return new JsonResult<>().fail();
    }

    /**
     * 用户名重复检验
     *
     * @param username 用户名
     * @return 响应
     */
    @PostMapping("/user/check")
    @PassToken
    public JsonResult<Object> duplicateCheck(String username) {
        log.info("/user/check: {}", username);
        if (!StringUtils.hasLength(username)) {
            return new JsonResult<>().fail("用户名为空");
        }
        if (usernameDuplicateCheck(username)) {
            return new JsonResult<>().fail("用户名已存在");
        }
        return new JsonResult<>().ok();
    }


    /**
     * 用户注册
     *
     * @param userRegister 注册的用户（携带5条感兴趣问题）
     * @return 响应
     */
    @PostMapping("/user/register")
    @PassToken
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult<User> register(@RequestBody UserRegister userRegister) {
        User user = new User(userRegister);
        if (!StringUtils.hasLength(user.getUsername()) || !StringUtils.hasLength(user.getPassword())) {
            return new JsonResult<User>().fail();
        }
        if (usernameDuplicateCheck(user.getUsername())) {
            return new JsonResult<User>().fail("用户名已存在");
        }
        if (userService.save(user)) {
            userEntityService.addEntityUserList(userRegister);
            return new JsonResult<User>().ok(userService.getOne(new QueryWrapper<>(user)));
        } else {
            return new JsonResult<User>().fail();
        }
    }

    /**
     * 用户名重复校验
     * @param username 用户名
     * @return true为重复 false为无重复
     */
    private boolean usernameDuplicateCheck(String username) {
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(User::getUsername, username)
                .select(User::getUsername);
        long result = userService.count(userQuery);
        return result == 1;
    }




    /**
     * 随机实体
     *
     * @return 实体列表
     */
    @GetMapping("/interest/random")
    public JsonResult<List<EntityForQuestion>> getInterest() {
        List<Question> questionList = questionService.queryRandomTenQuestionList();
        List<EntityForQuestion> entityList = new ArrayList<>();
        for (Question item : questionList) {
            entityList.add(new EntityForQuestion(item));
        }
        return new JsonResult<List<EntityForQuestion>>().ok(entityList);
    }






}
