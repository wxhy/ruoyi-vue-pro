package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 药房药品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PharmacyDrugRespVO {

    @Schema(description = "药物id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5727")
    @ExcelProperty("药物id")
    private Long id;

    @Schema(description = "批准号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("批准号")
    private String approvalNumber;

    @Schema(description = "通用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("通用名称")
    private String commonName;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("规格")
    private String specifications;

    @Schema(description = "生产厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("生产厂家")
    private String manufacturer;

    @Schema(description = "剂型")
    @ExcelProperty("剂型")
    private String dosageForm;

    @Schema(description = "进价", requiredMode = Schema.RequiredMode.REQUIRED, example = "28895")
    @ExcelProperty("进价")
    private BigDecimal purchasePrice;

    @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "21704")
    @ExcelProperty("零售价")
    private BigDecimal retailPrice;

    @Schema(description = "会员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24857")
    @ExcelProperty("会员id")
    private Long userId;

    @Schema(description = "是否特别关注0：否1:是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否特别关注0：否1:是")
    private Integer watch;

    @Schema(description = "状态0：定标中 1：定标完成 2：定标失败", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态0：定标中 1：定标完成 2：定标失败")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String mark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}