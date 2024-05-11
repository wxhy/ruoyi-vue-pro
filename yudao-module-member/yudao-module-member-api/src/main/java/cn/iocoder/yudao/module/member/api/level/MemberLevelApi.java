package cn.iocoder.yudao.module.member.api.level;

import cn.iocoder.yudao.module.member.api.level.dto.MemberLevelRespDTO;

/**
 * 会员等级 API 接口
 *
 * @author owen
 */
public interface MemberLevelApi {

    /**
     * 获得会员等级
     *
     * @param id 会员等级编号
     * @return 会员等级
     */
    MemberLevelRespDTO getMemberLevel(Long id);

}
