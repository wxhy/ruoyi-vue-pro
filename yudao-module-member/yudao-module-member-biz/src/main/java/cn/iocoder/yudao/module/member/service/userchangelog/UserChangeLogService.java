package cn.iocoder.yudao.module.member.service.userchangelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.member.controller.admin.userchangelog.vo.*;
import cn.iocoder.yudao.module.member.dal.dataobject.userchangelog.UserChangeLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 会员变动记录 Service 接口
 *
 * @author 芋道源码
 */
public interface UserChangeLogService {

    /**
     * 创建会员变动记录
     *
     * @return 编号
     */
    Long createUserChangeLog(Long userId, Integer changeType, String mark);

    /**
     * 获得会员变动记录分页
     *
     * @param pageReqVO 分页查询
     * @return 会员变动记录分页
     */
    PageResult<UserChangeLogDO> getUserChangeLogPage(UserChangeLogPageReqVO pageReqVO);

}