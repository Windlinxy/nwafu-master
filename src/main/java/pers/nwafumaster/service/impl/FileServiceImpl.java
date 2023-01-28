package pers.nwafumaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.nwafumaster.service.FileService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-28 16:02
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file-service.path}")
    private String path = "";

    @Value("${file-service.image-url}")
    private String imageUrl="";

    public String test(){
        return path;
    }

    @Override
    public String fileStore(MultipartFile file) {
        String serverFilePart;
        String localFilePart;
        // 获取上传的文件名扩展名
        String disposition = file.getOriginalFilename();
        String suffix;
        if (disposition != null) {
            suffix = disposition.substring(disposition.lastIndexOf("."));
        } else {
            suffix = "";
        }
        File fileDisk = new File(path);

        if (!fileDisk.exists()) {
            fileDisk.mkdir();
        }

        // 随机的生成uuid，作为文件名的一部分。 加上刚才获取到的后缀作为最终文件名。
        String uuid = UUID.randomUUID() + "";
        String filename = uuid.substring(0, 13) + suffix;


        serverFilePart = imageUrl  + filename;

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
}
