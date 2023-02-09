package pers.nwafumaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.service.DiseaseService;
import pers.nwafumaster.mapper.DiseaseMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease>
        implements DiseaseService {

    @Override
    public Disease getAndCreFire(int diseaseId) {
        LambdaQueryWrapper<Disease> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Disease::getDiseaseId, diseaseId);
        Disease diseaseInDatabase = getOne(wrapper);
        if (diseaseInDatabase != null) {
            LambdaUpdateWrapper<Disease> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Disease::getDiseaseId, diseaseId);
            updateWrapper.setSql("fire = fire + 1");
            update(updateWrapper);
        }
        return diseaseInDatabase;
    }

}




