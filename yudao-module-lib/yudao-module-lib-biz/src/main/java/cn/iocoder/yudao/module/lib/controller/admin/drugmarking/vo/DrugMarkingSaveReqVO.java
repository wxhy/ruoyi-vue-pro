package cn.iocoder.yudao.module.lib.controller.admin.drugmarking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 药房药物对标新增/修改 Request VO")
@Data
public class DrugMarkingSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20772")
    private Long id;

    @Schema(description = "数据id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14206")
    @NotNull(message = "数据id不能为空")
    private Long dataId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21889")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "药房药物ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15249")
    @NotNull(message = "药房药物ID不能为空")
    private Long drugId;

}