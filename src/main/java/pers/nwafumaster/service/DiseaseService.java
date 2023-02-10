package pers.nwafumaster.service;

import pers.nwafumaster.beans.Disease;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author Windlinxy
 */
public interface DiseaseService extends IService<Disease> {

    Disease getAndCreFire(int diseaseId);

}
