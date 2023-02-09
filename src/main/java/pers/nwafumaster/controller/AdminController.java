package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.config.JwtConfig;
import pers.nwafumaster.service.DiseaseService;
import pers.nwafumaster.service.UserService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.MyPage;

import javax.annotation.Resource;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-29 19:21
 **/
@RestController
@RequestMapping(
        value = "/admin",
        produces = "application/json"
)
@Slf4j
public class AdminController {

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private UserService userService;

    @Resource
    private DiseaseService diseaseService;

    @GetMapping("/user")
    public JsonResult<MyPage<User>> userList(
            @RequestParam(value = "cur", defaultValue = "1") int currentPage,
            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        MyPage<User> myPage = new MyPage<>(currentPage, pageSize);
        return new JsonResult<MyPage<User>>().ok(userService.page(myPage));
    }

    @PostMapping("/disease")
    public JsonResult<Object> addDisease(@RequestBody Disease disease) {
        log.info("/admin/disease : {}", disease);
        if (disease == null) {
            return new JsonResult<>().fail();
        }
        if (StringUtils.hasLength(disease.getDiseaseName())) {
            if (StringUtils.hasLength(disease.getIntroduce())) {
                if (StringUtils.hasLength(disease.getControl())) {
                    if (StringUtils.hasLength(disease.getFeatures())) {
                        if (StringUtils.hasLength(disease.getDiseaseType())) {
                            if (diseaseService.save(disease)) {
                                return new JsonResult<>().ok();
                            }
                        }
                    }
                }
            }
        }
        return new JsonResult<>().fail();
    }

    @PostMapping("/disease/{id}")
    public JsonResult<Object> updateDisease(
            @PathVariable("id") int diseaseId,
            @RequestBody Disease disease) {
        if (disease == null) {
            return new JsonResult<>().fail();
        }
        LambdaUpdateWrapper<Disease> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Disease::getDiseaseId, diseaseId);
        return diseaseService.update(disease, updateWrapper) ?
                new JsonResult<>().ok()
                : new JsonResult<>().fail();
    }

    @DeleteMapping("/disease/{id}")
    public JsonResult<Object> deleteDiseaseById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int diseaseId
    ) {
        User user = jwtConfig.getUser(token);
        if ("admin".equals(user.getUsername()) && diseaseService.removeById(diseaseId)){
            return new JsonResult<>().ok();
        }
        return new JsonResult<>().fail();
    }

}
