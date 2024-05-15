package cn.iocoder.yudao.module.member.service.userchangelog;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.member.controller.admin.userchangelog.vo.*;
import cn.iocoder.yudao.module.member.dal.dataobject.userchangelog.UserChangeLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.member.dal.mysql.userchangelog.UserChangeLogMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.member.enums.ErrorCodeConstants.*;

/**
 * 会员变动记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class UserChangeLogServiceImpl implements UserChangeLogService {

    @Resource
    private UserChangeLogMapper userChangeLogMapper;

    @Override
    public Long createUserChangeLog(Long userId, Integer changeType, String mark) {
        // 插入
        UserChangeLogDO userChangeLog = new UserChangeLogDO();
        userChangeLog.setUserId(userId);
        userChangeLog.setChangeType(changeType);
        userChangeLog.setMark(mark);
        userChangeLogMapper.insert(userChangeLog);
        // 返回
        return userChangeLog.getId();
    }


    @Override
    public PageResult<UserChangeLogDO> getUserChangeLogPage(UserChangeLogPageReqVO pageReqVO) {
        return userChangeLogMapper.selectPage(pageReqVO);
    }

}