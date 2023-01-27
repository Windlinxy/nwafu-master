package pers.nwafumaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.service.UserService;
import pers.nwafumaster.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Windlinxy
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




