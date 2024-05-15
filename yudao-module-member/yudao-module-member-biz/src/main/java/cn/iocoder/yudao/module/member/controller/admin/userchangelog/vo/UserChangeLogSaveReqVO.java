package cn.iocoder.yudao.module.member.controller.admin.userchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 会员变动记录新增/修改 Request VO")
@Data
public class UserChangeLogSaveReqVO {

    @Schema(description = "会员变动记录id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14356")
    private Long id;

    @Schema(description = "会员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "692")
    @NotNull(message = "会员id不能为空")
    private Long userId;

    @Schema(description = "变动类型0：续费1：调整等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "变动类型0：续费1：调整等级不能为空")
    private Integer changeType;

    @Schema(description = "变动原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "变动原因不能为空")
    private String mark;

}