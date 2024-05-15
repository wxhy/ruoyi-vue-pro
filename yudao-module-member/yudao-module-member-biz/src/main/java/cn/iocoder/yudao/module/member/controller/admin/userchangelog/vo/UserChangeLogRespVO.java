package cn.iocoder.yudao.module.member.controller.admin.userchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 会员变动记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserChangeLogRespVO {

    @Schema(description = "会员变动记录id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14356")
    @ExcelProperty("会员变动记录id")
    private Long id;

    @Schema(description = "会员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "692")
    @ExcelProperty("会员id")
    private Long userId;

    @Schema(description = "变动类型0：续费1：调整等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("变动类型0：续费1：调整等级")
    private Integer changeType;

    @Schema(description = "变动原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("变动原因")
    private String mark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}