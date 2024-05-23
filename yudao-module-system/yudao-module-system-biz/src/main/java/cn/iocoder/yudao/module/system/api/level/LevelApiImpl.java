package cn.iocoder.yudao.module.system.api.level;

import cn.iocoder.yudao.module.system.api.level.dto.LevelDTO;
import cn.iocoder.yudao.module.system.convert.level.LevelConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import cn.iocoder.yudao.module.system.service.level.LevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qiuhongyun
 * @date 2024-05-23 15:13
 */
@Service
public class LevelApiImpl implements LevelApi{

    @Resource
    private LevelService levelService;
    /**
     * 查询会员等级
     *
     * @param id
     * @return
     */
    @Override
    public LevelDTO getLevel(Long id) {
        LevelDO level = levelService.getLevel(id);
        return LevelConvert.INSTANCE.convert02(level);
    }
}
