package cn.iocoder.yudao.module.lib.controller.admin.marking.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author qiuhongyun
 * @date 2024-06-05 9:52
 */
@Data
public class MarkingDrugInfo {
    private Long id;

    private Long dataId;

    private String name;

    @Schema(description = "批准号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("批准号")
    private String approvalNumber;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("规格")
    private String packing;

    @Schema(description = "剂型")
    @ExcelProperty("剂型")
    private String dosageForm;

    @Schema(description = "生产厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("生产厂家")
    private String productionEnterPrise;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "13856")
    @ExcelProperty("价格")
    private BigDecimal price;

    @Schema(description = "售卖店铺数量", example = "15570")
    @ExcelProperty("售卖店铺数量")
    private String shopCount;
}
