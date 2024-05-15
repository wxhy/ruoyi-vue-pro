package cn.iocoder.yudao.module.lib.controller.admin.drugmarking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 药房药物对标 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DrugMarkingRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20772")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "数据id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14206")
    @ExcelProperty("数据id")
    private Long dataId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21889")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "药房药物ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15249")
    @ExcelProperty("药房药物ID")
    private Long drugId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}