package cn.iocoder.yudao.module.member.api.level.dto;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * 会员等级 Resp DTO
 *
 * @author 芋道源码
 */
@Data
public class MemberLevelRespDTO {

    /**
     * 编号
     */
    private Long id;
    /**
     * 等级名称
     */
    private String name;
    /**
     * 等级
     */
    private Integer level;

    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 最大关注数量
     */
    private Integer watchCount;

}
