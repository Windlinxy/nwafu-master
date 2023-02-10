package pers.nwafumaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.nwafumaster.beans.Interest;
import pers.nwafumaster.beans.Question;
import pers.nwafumaster.service.InterestService;
import pers.nwafumaster.mapper.InterestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author Windlinxy
 */
@Service
public class InterestServiceImpl extends ServiceImpl<InterestMapper, Interest>
        implements InterestService {
    @Resource
    private InterestMapper interestMapper;

    @Override
    public List<Interest> queryRandomTenList() {
        List<Interest>list;
        if ((list = interestMapper.selectListRandomTen()) != null) {
            return list;
        }
        log.warn("未取到问题");
        return null;
    }
}




