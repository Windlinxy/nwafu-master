package pers.nwafumaster.service;

import pers.nwafumaster.beans.Interest;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.nwafumaster.beans.Question;

import java.util.List;

/**
 *
 */
public interface InterestService extends IService<Interest> {
    List<Interest> queryRandomTenList();
}
