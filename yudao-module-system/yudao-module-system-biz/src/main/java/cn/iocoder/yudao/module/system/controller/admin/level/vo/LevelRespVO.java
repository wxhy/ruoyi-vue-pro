package cn.iocoder.yudao.module.system.controller.admin.level.vo;

import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 用户等级 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LevelRespVO {

    private Long id;

    @Schema(description = "等级名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("等级名称")
    private String name;

    @Schema(description = "最大关注数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty("最大关注数量")
    private Integer watchCount;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "等级图标", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("等级图标")
    private String icon;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.INFRA_INTEGER_STRING)
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}