package pers.nwafumaster.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Windlinxy
 * @description
 * @create 2023-01-28 16:01
 **/
public interface FileService {
    /**
     * 文件存储服务
     *
     * @param file       上传文件
     * @return java.lang.String 文件web中路径
     * @date 20:11 2021/10/17
     **/
    String fileStore(MultipartFile file);
}
