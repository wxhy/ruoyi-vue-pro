package cn.iocoder.yudao.module.member.dal.dataobject.userchangelog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 会员变动记录 DO
 *
 * @author 芋道源码
 */
@TableName("member_user_change_log")
@KeySequence("member_user_change_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeLogDO extends BaseDO {

    /**
     * 会员变动记录id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 会员id
     */
    private Long userId;
    /**
     * 变动类型0：续费1：调整等级
     */
    private Integer changeType;
    /**
     * 变动原因
     */
    private String mark;

}