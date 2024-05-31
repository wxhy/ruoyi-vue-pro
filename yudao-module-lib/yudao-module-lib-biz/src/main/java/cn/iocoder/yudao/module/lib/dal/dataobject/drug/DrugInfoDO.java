package cn.iocoder.yudao.module.lib.dal.dataobject.drug;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 药品爬虫 DO
 *
 * @author 芋道源码
 */
@TableName("drug_info")
@KeySequence("drug_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugInfoDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 药品名
     */
    private String name;
    /**
     * 批准文号
     */
    private String approvalNumber;
    /**
     * 规格
     */
    private String packing;
    /**
     * 包装
     */
    private String dosageForm;
    /**
     * 生产厂家
     */
    private String productionEnterPrise;
    /**
     * 价格
     */
    private String price;
    /**
     * 售卖店家数量
     */
    private String shopCount;
    /**
     * 图片
     */
    private String imgInfo;
    /**
     * 来源地址
     */
    private String url;

}