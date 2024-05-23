package cn.iocoder.yudao.module.system.service.level;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.level.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 用户等级 Service 接口
 *
 * @author 芋道源码
 */
public interface LevelService {

    /**
     * 创建用户等级
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLevel(@Valid LevelSaveReqVO createReqVO);

    /**
     * 更新用户等级
     *
     * @param updateReqVO 更新信息
     */
    void updateLevel(@Valid LevelSaveReqVO updateReqVO);

    /**
     * 删除用户等级
     *
     * @param id 编号
     */
    void deleteLevel(Long id);

    /**
     * 获得用户等级
     *
     * @param id 编号
     * @return 用户等级
     */
    LevelDO getLevel(Long id);

    /**
     * 获得用户等级分页
     *
     * @param pageReqVO 分页查询
     * @return 用户等级分页
     */
    PageResult<LevelDO> getLevelPage(LevelPageReqVO pageReqVO);

    /**
     * 获得用户等级列表
     *
     * @return 用户等级列表
     */
    List<LevelDO> getEnableLevelList();

    /**
     * 获得用户等级 Map
     *
     * @param ids 编号列表
     * @return 用户等级 Map
     */
    Map<Long,LevelDO> getLevelMap(Collection<Long> ids);
}