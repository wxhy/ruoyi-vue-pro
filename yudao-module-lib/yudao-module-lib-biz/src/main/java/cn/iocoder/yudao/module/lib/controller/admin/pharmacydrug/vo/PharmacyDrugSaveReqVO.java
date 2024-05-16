package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 药房药品新增/修改 Request VO")
@Data
public class PharmacyDrugSaveReqVO {

    @Schema(description = "药物id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5727")
    private Long id;

    @Schema(description = "批准号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "批准号不能为空")
    private String approvalNumber;

    @Schema(description = "通用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "通用名称不能为空")
    private String commonName;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "规格不能为空")
    private String specifications;

    @Schema(description = "生产厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "生产厂家不能为空")
    private String manufacturer;

    @Schema(description = "剂型")
    private String dosageForm;

    @Schema(description = "进价", requiredMode = Schema.RequiredMode.REQUIRED, example = "28895")
    @NotNull(message = "进价不能为空")
    private BigDecimal purchasePrice;

    @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "21704")
    @NotNull(message = "零售价不能为空")
    private BigDecimal retailPrice;

}