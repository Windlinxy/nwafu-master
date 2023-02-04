package pers.nwafumaster.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.nwafumaster.beans.Message;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.config.JwtConfig;
import pers.nwafumaster.service.MessageService;
import pers.nwafumaster.vo.JsonResult;
import pers.nwafumaster.vo.MyPage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @Resource
    private MessageService messageService;

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 添加留言
     *
     * @param message 留言
     * @return 响应
     */
    @PostMapping()
    public JsonResult<Object> messageAdd(
            HttpServletRequest request,
            @RequestBody Message message) {
        String token = request.getHeader("Authorization");
        User user = jwtConfig.getUser(token);
        if (message != null) {
            if (StringUtils.hasLength(message.getTitle()) && StringUtils.hasLength(message.getContent())) {
                message.setUserId(user.getUserId());
                if (messageService.save(message)) {
                    return new JsonResult<>().ok();
                }
            }
        }
        return new JsonResult<>().fail();
    }

    /**
     * 查看留言
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return 响应
     */
    @GetMapping()
    public JsonResult<MyPage<Message>> getMessages(
            @RequestParam("cur") int currentPage,
            @RequestParam("size") int pageSize) {
        MyPage<Message> myPage = new MyPage<>(currentPage, pageSize);
        return new JsonResult<MyPage<Message>>().ok(messageService.page(myPage));
    }

    @GetMapping("/my")
    public JsonResult<MyPage<Message>> getMessagesPersonal(
            HttpServletRequest request,
            @RequestParam("cur") int currentPage,
            @RequestParam("size") int pageSize) {
        String token = request.getHeader("Authorization");
        User user = jwtConfig.getUser(token);
        MyPage<Message> myPage = new MyPage<>(currentPage, pageSize);
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getUserId, user.getUserId());
        return new JsonResult<MyPage<Message>>().ok(messageService.page(myPage, wrapper));
    }

    @DeleteMapping()
    public JsonResult<Object> deleteMessage(
            HttpServletRequest request,
            @RequestParam("messageId") Integer messageId) {
        String token = request.getHeader("Authorization");
        User user = jwtConfig.getUser(token);
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getUserId, user.getUserId()).eq(Message::getMessageId, messageId);
        if (messageService.remove(queryWrapper)) {
            return new JsonResult<>().ok();
        }
        return new JsonResult<>().fail();
    }
}
