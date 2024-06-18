package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 药房药品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PharmacyDrugPageReqVO extends PageParam {

    @Schema(description = "批准号")
    private String approvalNumber;

    @Schema(description = "通用名称", example = "赵六")
    private String commonName;

    @Schema(description = "生产厂家")
    private String manufacturer;

    @Schema(description = "产品批次")
    private String productBatch;

    @Schema(description = "会员id", example = "24857")
    private Long userId;

    @Schema(description = "是否特别关注0：否1:是")
    private Integer watch;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    private Integer status;

}