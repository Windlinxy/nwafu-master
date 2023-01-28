package pers.nwafumaster;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import pers.nwafumaster.service.FileService;
import pers.nwafumaster.service.impl.FileServiceImpl;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("dev")
class NwafuMasterApplicationTests {

    private FileServiceImpl fileService;

    @Resource
    public void setFileService(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @Test
    void contextLoads() {
        System.out.println(fileService.test());
    }

}
