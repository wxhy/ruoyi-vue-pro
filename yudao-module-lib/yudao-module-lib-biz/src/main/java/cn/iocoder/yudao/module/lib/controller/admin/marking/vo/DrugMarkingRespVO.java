package cn.iocoder.yudao.module.lib.controller.admin.marking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
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

    /**
     * 名称
     */
    private String name;
    /**
     * 批准号
     */
    private String approvalNumber;
    /**
     * 规格
     */
    private String packing;
    /**
     * 剂型
     */
    private String dosageForm;
    /**
     * 生产厂家
     */
    private String productionEnterPrise;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 售卖店铺数量
     */
    private String shopCount;
    /**
     * 药品图片
     */
    private String imgInfo;

}