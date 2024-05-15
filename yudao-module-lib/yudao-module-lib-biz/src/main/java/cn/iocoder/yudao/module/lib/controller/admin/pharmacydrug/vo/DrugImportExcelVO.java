package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author qiuhongyun
 * @date 2024-05-15 17:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class DrugImportExcelVO {

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

    @Schema(description = "产品批次")
    @ExcelProperty("产品批次")
    private String productBatch;

    @Schema(description = "生产日期")
    @ExcelProperty("生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    @ExcelProperty("有效期至")
    private LocalDate indate;
}
