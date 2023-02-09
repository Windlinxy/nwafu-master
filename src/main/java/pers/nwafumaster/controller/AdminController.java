package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.annotation.AdminCheck;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.beans.User;
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
@AdminCheck
public class AdminController {
    @Resource
    private UserService userService;

    @Resource
    private DiseaseService diseaseService;

    /**
     * 获取用户列表
     *
     * @param currentPage 当前页码
     * @param pageSize    页面信息数
     * @return 页面
     */
    @GetMapping("/user")
    public JsonResult<MyPage<User>> userList(
            @RequestParam(value = "cur", defaultValue = "1") int currentPage,
            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        MyPage<User> myPage = new MyPage<>(currentPage, pageSize);
        return new JsonResult<MyPage<User>>().ok(userService.page(myPage));
    }

    /**
     * 添加病虫害信息
     *
     * @param disease 详细信息
     * @return 响应
     */
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

    /**
     * 更新病虫害信息
     *
     * @param diseaseId 病虫害id
     * @param disease   详细信息
     * @return 响应
     */
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

    /**
     * 删除病虫害
     *
     * @param diseaseId 病虫害id
     * @return 响应
     */
    @DeleteMapping("/disease/{id}")
    public JsonResult<Object> deleteDiseaseById(
            @PathVariable("id") int diseaseId
    ) {
        if (diseaseService.removeById(diseaseId)) {
            return new JsonResult<>().ok();
        }
        return new JsonResult<>().fail();
    }

}
