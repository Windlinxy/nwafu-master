package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.annotation.AdminCheck;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.beans.Interest;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.service.DiseaseService;
import pers.nwafumaster.service.InterestService;
import pers.nwafumaster.service.UserEntityService;
import pers.nwafumaster.service.UserService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.MyPage;

import javax.annotation.Resource;
import javax.security.auth.kerberos.KerberosKey;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-29 19:21
 **/
@RestController
@RequestMapping(value = "/admin", produces = "application/json")
@Slf4j(topic = "AdminController")
@AdminCheck
public class AdminController {
    @Resource
    private UserService userService;

    @Resource
    private DiseaseService diseaseService;

    @Resource
    private InterestService interestService;

    @Resource
    private UserEntityService userEntityService;

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
    public JsonResult<Object> updateDisease(@PathVariable("id") int diseaseId, @RequestBody Disease disease) {
        if (disease == null) {
            return new JsonResult<>().fail();
        }
        LambdaUpdateWrapper<Disease> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Disease::getDiseaseId, diseaseId);
        return new JsonResult<>().jud(diseaseService.update(disease, updateWrapper));
    }

    /**
     * 删除病虫害
     *
     * @param diseaseId 病虫害id
     * @return 响应
     */
    @DeleteMapping("/disease/{id}")
    public JsonResult<Object> deleteDiseaseById(@PathVariable("id") int diseaseId) {
        return new JsonResult<>().jud(diseaseService.removeById(diseaseId));
    }

    /**
     * 添加推荐实体
     *
     * @param interest 实体
     * @return 响应
     */
    @PostMapping("/interest")
    public JsonResult<Object> addInterest(@RequestBody Interest interest) {
        if (interest == null) {
            return new JsonResult<>().fail();
        }
        if (StringUtils.hasLength(interest.getEntity()) && interest.getDiseaseId() != null && interest.getDiseaseId() != 0) {
            LambdaQueryWrapper<Disease> eq = new LambdaQueryWrapper<Disease>().eq(Disease::getDiseaseId, interest.getDiseaseId());
            if (diseaseService.getOne(eq) != null && interestService.save(interest)) {
                return new JsonResult<>().ok();
            }
        }
        return new JsonResult<>().fail();
    }

    /**
     * 推荐实体
     *
     * @param interestId id
     * @return 响应
     */
    @GetMapping("/interest/{id}")
    public JsonResult<Interest> getInterest(@PathVariable("id") int interestId) {
        return new JsonResult<Interest>().ok(interestService.getById(interestId));
    }


    /**
     * page
     *
     * @param currentPage 当前页码
     * @param pageSize    页面信息数
     * @return 响应
     */
    @GetMapping("/interest")
    public JsonResult<MyPage<Interest>> interestPage(
            @RequestParam(value = "cur", defaultValue = "1") int currentPage,
            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        MyPage<Interest> myPage = new MyPage<>(currentPage, pageSize);
        return new JsonResult<MyPage<Interest>>().ok(interestService.page(myPage));
    }

    /**
     * 更新推荐详情
     *
     * @param interest   推按
     * @param interestId id
     * @return 响应
     */
    @PostMapping("/interest/{id}")
    public JsonResult<Interest> upInterest(
            @RequestBody Interest interest,
            @PathVariable("id") int interestId) {
        interest.setInterestedId(interestId);
        return new JsonResult<Interest>().jud(interestService.updateById(interest));
    }

    /**
     * 删除推荐
     *
     * @param interestId id
     * @return 响应
     */
    @DeleteMapping("/interest/{id}")
    public JsonResult<Object> deleteInterestById(@PathVariable("id") int interestId) {
        return new JsonResult<>().jud(interestService.removeById(interestId));
    }


    /**
     * 为实体添加服务，返回病虫害id和名
     *
     * @param currentPage 当前页
     * @param pageSize    页面信息数
     * @return 响应
     */
    @GetMapping("/disease")
    public JsonResult<MyPage<Disease>> diseaseList(
            @RequestParam(value = "cur", defaultValue = "1") int currentPage,
            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        MyPage<Disease> myPage = new MyPage<>(currentPage, pageSize);
        LambdaQueryWrapper<Disease> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Disease::getDiseaseId, Disease::getDiseaseName);
        return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, queryWrapper));
    }

    /**
     * 导出excel
     *
     * @param response 响应
     * @throws IOException io异常
     */
    @GetMapping(value = "/excel", produces = "application/vnd.ms-excel;charset=UTF-8")
    public void excelExport(HttpServletResponse response) throws IOException {
        userEntityService.excelExport(response);
    }
}
