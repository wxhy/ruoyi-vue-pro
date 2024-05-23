package cn.iocoder.yudao.module.system.dal.mysql.level;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.level.vo.*;

/**
 * 用户等级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LevelMapper extends BaseMapperX<LevelDO> {

    default PageResult<LevelDO> selectPage(LevelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LevelDO>()
                .likeIfPresent(LevelDO::getName, reqVO.getName())
                .eqIfPresent(LevelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(LevelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LevelDO::getId));
    }

    default LevelDO selectByName(String name) {
        return selectOne(LevelDO::getName, name);
    }

}