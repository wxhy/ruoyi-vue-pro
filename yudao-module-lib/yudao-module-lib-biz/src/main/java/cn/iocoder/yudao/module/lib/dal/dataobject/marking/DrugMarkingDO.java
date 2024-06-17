package cn.iocoder.yudao.module.lib.dal.dataobject.marking;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 药房药物对标 DO
 *
 * @author 芋道源码
 */
@TableName("lib_drug_marking")
@KeySequence("lib_drug_marking_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugMarkingDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 数据id
     */
    private Long dataId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 药房药物ID
     */
    private Long drugId;

    /**
     * 名称
     */
    private String name;
    /**
     * 批准号
     */
    private String approvalNumber;
    /**
     * 规格
     */
    private String packing;
    /**
     * 剂型
     */
    private String dosageForm;
    /**
     * 生产厂家
     */
    private String productionEnterPrise;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 售卖店铺数量
     */
    private String shopCount;
    /**
     * 药品图片
     */
    private String imgInfo;

}