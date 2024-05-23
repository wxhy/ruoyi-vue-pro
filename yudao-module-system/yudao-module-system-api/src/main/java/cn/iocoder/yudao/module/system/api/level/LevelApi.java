package cn.iocoder.yudao.module.system.api.level;

import cn.iocoder.yudao.module.system.api.level.dto.LevelDTO;

public interface LevelApi {

    /**
     * 查询会员等级
     * @param id
     * @return
     */
    LevelDTO getLevel(Long id);
}
