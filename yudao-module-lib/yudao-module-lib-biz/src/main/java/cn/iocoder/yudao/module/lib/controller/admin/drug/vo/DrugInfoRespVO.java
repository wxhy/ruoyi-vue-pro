package cn.iocoder.yudao.module.lib.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 药品爬虫 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DrugInfoRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9334")
    @ExcelProperty("id")
    private Integer id;

    @Schema(description = "药品名", example = "张三")
    @ExcelProperty("药品名")
    private String name;

    @Schema(description = "批准文号")
    @ExcelProperty("批准文号")
    private String approvalNumber;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String packing;

    @Schema(description = "包装")
    @ExcelProperty("包装")
    private String dosageForm;

    @Schema(description = "生产厂家")
    @ExcelProperty("生产厂家")
    private String productionEnterPrise;

    @Schema(description = "价格", example = "13476")
    @ExcelProperty("价格")
    private String price;

    @Schema(description = "售卖店家数量", example = "30832")
    @ExcelProperty("售卖店家数量")
    private String shopCount;

    @Schema(description = "图片")
    @ExcelProperty("图片")
    private String imgInfo;

    @Schema(description = "来源地址", example = "https://www.iocoder.cn")
    @ExcelProperty("来源地址")
    private String url;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}