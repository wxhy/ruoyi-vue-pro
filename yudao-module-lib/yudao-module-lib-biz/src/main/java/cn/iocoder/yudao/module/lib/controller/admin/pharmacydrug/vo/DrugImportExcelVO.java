package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import cn.iocoder.yudao.framework.excel.core.convert.MoneyConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Schema(description = "批准号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("批准号")
    @NotBlank(message = "批准号不能为空")
    private String approvalNumber;

    @Schema(description = "通用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("通用名称")
    @NotBlank(message = "通用名称不能为空")
    private String commonName;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("规格")
    @NotBlank(message = "规格不能为空")
    private String specifications;

    @Schema(description = "生产厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("生产厂家")
    @NotBlank(message = "生产厂家不能为空")
    private String manufacturer;

    @Schema(description = "剂型")
    @ExcelProperty("剂型")
    private String dosageForm;

    @Schema(description = "进价", requiredMode = Schema.RequiredMode.REQUIRED, example = "28895")
    @ExcelProperty(value = "进价", converter = MoneyConvert.class)
    @NotNull(message = "进价不能为空")
    private BigDecimal purchasePrice;

    @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "21704")
    @ExcelProperty(value = "零售价", converter = MoneyConvert.class)
    @NotNull(message = "零售价不能为空")
    private BigDecimal retailPrice;
}
