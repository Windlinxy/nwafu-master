package pers.nwafumaster.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.beans.Disease;
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

    private UserService userService;

    private DiseaseService diseaseService;

    @Resource
    public void setDiseaseService(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public JsonResult<MyPage<User>> userList(
            @RequestParam("cur") int currentPage,
            @RequestParam("size") int pageSize) {
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


}
