package pers.nwafumaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.nwafumaster.service.FileService;
import pers.nwafumaster.vo.JsonResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-28 16:49
 **/
@RestController
@RequestMapping(
        produces = "application/json"
)
public class FileController {

    private FileService fileService;

    @Resource
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    public JsonResult<Object> putImage(HttpServletRequest request,
                                       @RequestParam("image") MultipartFile file){
        Map<String, Object> map = new HashMap<>(4);
        if(file.isEmpty()){
            return new JsonResult<>().fail("图片不能为空");
        }
        String imageUrl = fileService.fileStore(file);
        return new JsonResult<>().ok(imageUrl);
    }
}
