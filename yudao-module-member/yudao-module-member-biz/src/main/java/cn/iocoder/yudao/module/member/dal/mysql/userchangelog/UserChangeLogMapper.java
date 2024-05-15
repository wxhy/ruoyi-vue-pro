package cn.iocoder.yudao.module.member.dal.mysql.userchangelog;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.member.dal.dataobject.userchangelog.UserChangeLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.member.controller.admin.userchangelog.vo.*;

/**
 * 会员变动记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface UserChangeLogMapper extends BaseMapperX<UserChangeLogDO> {

    default PageResult<UserChangeLogDO> selectPage(UserChangeLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserChangeLogDO>()
                .eqIfPresent(UserChangeLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserChangeLogDO::getChangeType, reqVO.getChangeType())
                .betweenIfPresent(UserChangeLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserChangeLogDO::getId));
    }

}