package pers.nwafumaster.service.impl;

import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.nwafumaster.service.FileService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-28 16:02
 **/
@Slf4j
@ConfigurationProperties(prefix = "file-service")
@Setter
@Service
public class FileServiceImpl implements FileService {

    /**
     * 文件路径
     */
    String path = "/";


    /**
     * 图片链接
     */
    String imageUrl = "/";


    @Override
    public String fileStore(MultipartFile file) {
        String serverFilePart;
        String localFilePart;
        // 获取上传的文件名扩展名
        String disposition = file.getOriginalFilename();
        String suffix = disposition != null ? disposition.substring(disposition.lastIndexOf(".")) : "";
        File fileDisk = new File(path);
//        if (!fileDisk.exists()) {
//            fileDisk.mkdir();
//        }

        // 随机的生成uuid，作为文件名的一部分。 加上刚才获取到的后缀作为最终文件名。
        String uuid = UUID.randomUUID() + "";
        String filename = uuid.substring(0, 13) + suffix;
        serverFilePart = imageUrl + filename;
        localFilePart = path + filename;

        log.info("存储路径：" + localFilePart);
        try {
            file.transferTo(new File(localFilePart));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return serverFilePart;
    }

    @Override
    public List<String> multiFileStore(MultipartFile[] files) {
        ArrayList<String> urlList = new ArrayList<>();
        for(MultipartFile file : files){
            urlList.add(fileStore(file));
        }
        return urlList;
    }
}
