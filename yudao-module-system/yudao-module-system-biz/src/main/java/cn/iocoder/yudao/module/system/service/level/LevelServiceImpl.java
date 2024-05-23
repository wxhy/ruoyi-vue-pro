package cn.iocoder.yudao.module.system.service.level;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.level.vo.LevelPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.level.vo.LevelSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.level.LevelMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.LEVEL_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.ERROR_LEVEL_NAME_EXISTS;
/**
 * 用户等级 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class LevelServiceImpl implements LevelService {

    @Resource
    private LevelMapper levelMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createLevel(LevelSaveReqVO createReqVO) {
        // 插入
        LevelDO level = BeanUtils.toBean(createReqVO, LevelDO.class);
        LevelDO levelDO = levelMapper.selectByName(level.getName());
        if (Objects.nonNull(levelDO)) {
            throw exception(ERROR_LEVEL_NAME_EXISTS);
        }

        levelMapper.insert(level);
        // 返回
        return level.getId();
    }

    @Override
    public void updateLevel(LevelSaveReqVO updateReqVO) {
        // 校验存在
        LevelDO levelDO = levelMapper.selectById(updateReqVO.getId());
        if (Objects.isNull(levelDO)) {
            throw exception(LEVEL_NOT_EXISTS);
        }
        LevelDO levelByName = levelMapper.selectByName(updateReqVO.getName());
        if (Objects.nonNull(levelByName) && !updateReqVO.getId().equals(levelByName.getId())) {
            throw exception(ERROR_LEVEL_NAME_EXISTS);
        }

        // 更新
        LevelDO updateObj = BeanUtils.toBean(updateReqVO, LevelDO.class);
        levelMapper.updateById(updateObj);
    }

    @Override
    public void deleteLevel(Long id) {
        // 校验存在
        validateLevelExists(id);
        Long selectCount = adminUserMapper.selectCount(AdminUserDO::getLevelId, id);
        if (selectCount > 0) {
            throw exception(LEVEL_NOT_EXISTS);
        }
        // 删除
        levelMapper.deleteById(id);
    }

    private void validateLevelExists(Long id) {
        if (levelMapper.selectById(id) == null) {
            throw exception(LEVEL_NOT_EXISTS);
        }
    }

    @Override
    public LevelDO getLevel(Long id) {
        return levelMapper.selectById(id);
    }

    @Override
    public PageResult<LevelDO> getLevelPage(LevelPageReqVO pageReqVO) {
        return levelMapper.selectPage(pageReqVO);
    }

    /**
     * 获得用户等级列表
     *
     * @return 用户等级列表
     */
    @Override
    public List<LevelDO> getEnableLevelList() {
        return levelMapper.selectList(LevelDO::getStatus, CommonStatusEnum.ENABLE.getStatus());
    }

    /**
     * 获得用户等级 Map
     *
     * @param ids 编号列表
     * @return 用户等级 Map
     */
    @Override
    public Map<Long, LevelDO> getLevelMap(Collection<Long> ids) {
        List<LevelDO> levelDOS = levelMapper.selectBatchIds(ids);
        return CollectionUtils.convertMap(levelDOS, LevelDO::getId);
    }
}