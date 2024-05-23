package cn.iocoder.yudao.module.system.controller.admin.level.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 用户等级新增/修改 Request VO")
@Data
public class LevelSaveReqVO {


    @Schema(description = "等级id", example = "1024")
    private Long id;

    @Schema(description = "等级名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "等级名称不能为空")
    private String name;

    @Schema(description = "最大关注数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "最大关注数量不能为空")
    private Integer watchCount;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "等级图标", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "等级图标不能为空")
    private String icon;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}