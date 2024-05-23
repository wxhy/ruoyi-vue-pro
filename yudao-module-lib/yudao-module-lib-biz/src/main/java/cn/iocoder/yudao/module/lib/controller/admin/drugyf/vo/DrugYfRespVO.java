package cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 药房药品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DrugYfRespVO {

    @Schema(description = "药物id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25720")
    @ExcelProperty("药物id")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
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

    @Schema(description = "药品图片")
    @ExcelProperty("药品图片")
    private String imgInfo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}