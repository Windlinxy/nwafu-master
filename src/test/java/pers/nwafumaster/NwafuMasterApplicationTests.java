package pers.nwafumaster;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import pers.nwafumaster.service.FileService;
import pers.nwafumaster.service.impl.FileServiceImpl;
import pers.nwafumaster.vo.UserRegister;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

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
//        System.out.println(fileService.test());
        UserRegister userRegister = new UserRegister();
        int [] array = new int[] {1,2,3,4,5};
        userRegister.setInterestQuestions(array);
        System.out.println(Arrays.toString(array));
    }

}
