package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.beans.UserEntity;
import pers.nwafumaster.service.DiseaseService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.MyPage;

import javax.annotation.Resource;

/**
 * @author Windlinxy
 * @description
 * @date 2023-02-15 16:05
 **/
@RestController
@RequestMapping(
        produces = "application/json"
)
@Slf4j
public class DiseaseController {
    @Resource
    private DiseaseService diseaseService;

    /**
     * 病虫害信息查询
     *
     * @param diseaseName 病虫害名
     * @param diseaseType 病虫害类型
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return 响应
     */
    @GetMapping("/disease")
    public JsonResult<MyPage<Disease>> diseaseList(
            @RequestParam("name") String diseaseName,
            @RequestParam("type") String diseaseType,
            @RequestParam(value = "cur", defaultValue = "1") int currentPage,
            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        MyPage<Disease> myPage = new MyPage<>(currentPage, pageSize);
        LambdaQueryWrapper<Disease> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Disease::getDiseaseId);
        if (StringUtils.hasLength(diseaseType)) {
            return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, queryWrapper.eq(Disease::getDiseaseType, diseaseType)));
        }
        if (StringUtils.hasLength(diseaseName)) {
            return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, queryWrapper.like(Disease::getDiseaseName, diseaseName)));
        }
        return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, queryWrapper));
    }


    /**
     * 获取病虫害详情
     *
     * @param diseaseId 病虫害id
     * @return 响应
     */
    @GetMapping("/disease/{id}")
    public JsonResult<Disease> getDisease(@PathVariable("id") int diseaseId) {
        Disease diseaseInDatabase = diseaseService.getAndCreFire(diseaseId);
        return (diseaseInDatabase != null) ?
                new JsonResult<Disease>().ok(diseaseInDatabase)
                : new JsonResult<Disease>().fail("没有找到信息");
    }


    /**
     * 热门病虫害————未使用
     *
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return 响应
     */
    @GetMapping("/disease/fire")
    public JsonResult<MyPage<Disease>> fireDiseaseList(
            @RequestParam(value = "cur", defaultValue = "1") int currentPage,
            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        MyPage<Disease> myPage = new MyPage<>(currentPage, pageSize);
        LambdaQueryWrapper<Disease> queryWrapper = new LambdaQueryWrapper<Disease>()
                .select(Disease::getDiseaseId, Disease::getDiseaseName, Disease::getSImgUrl)
                .orderByDesc(true, Disease::getFire);
        return new JsonResult<MyPage<Disease>>().ok(diseaseService.page(myPage, queryWrapper));
    }
}
