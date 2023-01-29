package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.beans.Message;
import pers.nwafumaster.service.MessageService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.MyPage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Windlinxy
 * @description 留言相关
 * @date 2023-01-29 17:02
 **/
@RestController
@RequestMapping(value = "/message",
        produces = "application/json"
)
@Slf4j
public class MessageController {
    private MessageService messageService;

    @Resource
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping()
    public JsonResult<Object> messageAdd(@RequestBody Message message) {
        if (message != null) {
            if (StringUtils.hasLength(message.getTitle()) && StringUtils.hasLength(message.getContent())) {
                if (message.getUserId() != null && message.getUserId() != 0) {
                    if (messageService.save(message)) {
                        return new JsonResult<>().ok();
                    }
                }
            }
        }
        return new JsonResult<>().fail();
    }

    @GetMapping()
    public JsonResult<MyPage<Message>> getMessages(
            @RequestParam("cur") int currentPage,
            @RequestParam("size") int pageSize) {
        MyPage<Message> myPage = new MyPage<>(currentPage, pageSize);
        return new JsonResult<MyPage<Message>>().ok(messageService.page(myPage));
    }
}
