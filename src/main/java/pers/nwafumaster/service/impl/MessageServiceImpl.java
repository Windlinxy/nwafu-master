package pers.nwafumaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.nwafumaster.beans.Message;
import pers.nwafumaster.service.MessageService;
import pers.nwafumaster.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




