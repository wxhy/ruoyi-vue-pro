package cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 药房药品 DO
 *
 * @author 芋道源码
 */
@TableName("lib_pharmacy_drug")
@KeySequence("lib_pharmacy_drug_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDrugDO extends BaseDO {

    /**
     * 药物id
     */
    @TableId
    private Long id;
    /**
     * 批准号
     */
    private String approvalNumber;
    /**
     * 通用名称
     */
    private String commonName;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 生产厂家
     */
    private String manufacturer;
    /**
     * 剂型
     */
    private String dosageForm;
    /**
     * 进价
     */
    private BigDecimal purchasePrice;
    /**
     * 零售价
     */
    private BigDecimal retailPrice;
    /**
     * 产品批次
     */
    private String productBatch;
    /**
     * 生产日期
     */
    private LocalDate productionDate;
    /**
     * 有效期至
     */
    private LocalDate indate;
    /**
     * 会员id
     */
    private Long userId;
    /**
     * 是否特别关注0：否1:是
     */
    private Integer watch;
    /**
     * 状态0：定标中 1：定标完成 2：定标失败
     */
    private Integer status;
    /**
     * 备注
     */
    private String mark;

}