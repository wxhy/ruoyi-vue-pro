package cn.iocoder.yudao.module.member.api.level;

import cn.iocoder.yudao.module.member.api.level.dto.MemberLevelRespDTO;
import cn.iocoder.yudao.module.member.convert.level.MemberLevelConvert;
import cn.iocoder.yudao.module.member.service.level.MemberLevelService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.member.enums.ErrorCodeConstants.EXPERIENCE_BIZ_NOT_SUPPORT;

/**
 * 会员等级 API 实现类
 *
 * @author owen
 */
@Service
@Validated
public class MemberLevelApiImpl implements MemberLevelApi {

    @Resource
    private MemberLevelService memberLevelService;

    @Override
    public MemberLevelRespDTO getMemberLevel(Long id) {
        return MemberLevelConvert.INSTANCE.convert02(memberLevelService.getLevel(id));
    }


}
