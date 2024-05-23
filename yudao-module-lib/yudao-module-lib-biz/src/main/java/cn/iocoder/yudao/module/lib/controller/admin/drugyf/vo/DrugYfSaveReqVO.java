package cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 药房药品新增/修改 Request VO")
@Data
public class DrugYfSaveReqVO {

    @Schema(description = "药物id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25720")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "批准号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "批准号不能为空")
    private String approvalNumber;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "规格不能为空")
    private String packing;

    @Schema(description = "剂型")
    private String dosageForm;

    @Schema(description = "生产厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "生产厂家不能为空")
    private String productionEnterPrise;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "13856")
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @Schema(description = "售卖店铺数量", example = "15570")
    private String shopCount;

    @Schema(description = "药品图片")
    private String imgInfo;

    @Schema(description = "来源地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "来源地址不能为空")
    private String url;

}