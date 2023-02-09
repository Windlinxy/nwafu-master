package pers.nwafumaster.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.nwafumaster.service.FileService;
import pers.nwafumaster.vo.JsonResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Windlinxy
 * @description 文件存储相关
 * @date 2023-01-28 16:49
 **/
@RestController
@RequestMapping(
        produces = "application/json"
)
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 单文件上传
     *
     * @param image 文件
     * @return 响应
     */
    @PostMapping(value = "/image", consumes = "multipart/form-data")
    public JsonResult<Object> putImage(MultipartFile image) {
        if (image.isEmpty()) {
            return new JsonResult<>().fail("图片不能为空");
        }
        String imageUrl = fileService.fileStore(image);
        return new JsonResult<>().ok(imageUrl);
    }

    /**
     * 多文件上传
     *
     * @param images 文件s
     * @return 响应
     */
    @PostMapping(value = "/images", consumes = "multipart/form-data")
    public JsonResult<Object> uploadImages(MultipartFile[] images) {
        if (images[0].isEmpty()) {
            return new JsonResult<>().fail("图片不能为空");
        }
        List<String> imageUrlList = fileService.multiFileStore(images);
        return new JsonResult<>().ok(imageUrlList);
    }
}
