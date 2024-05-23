package cn.iocoder.yudao.module.system.dal.dataobject.level;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户等级 DO
 *
 * @author 芋道源码
 */
@TableName("system_level")
@KeySequence("system_level_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 等级名称
     */
    private String name;
    /**
     * 最大关注数量
     */
    private Integer watchCount;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 等级图标
     */
    private String icon;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}