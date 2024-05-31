package cn.iocoder.yudao.module.lib.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 药品爬虫新增/修改 Request VO")
@Data
public class DrugInfoSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9334")
    private Integer id;

    @Schema(description = "药品名", example = "张三")
    private String name;

    @Schema(description = "批准文号")
    private String approvalNumber;

    @Schema(description = "规格")
    private String packing;

    @Schema(description = "包装")
    private String dosageForm;

    @Schema(description = "生产厂家")
    private String productionEnterPrise;

    @Schema(description = "价格", example = "13476")
    private String price;

    @Schema(description = "售卖店家数量", example = "30832")
    private String shopCount;

    @Schema(description = "图片")
    private String imgInfo;

    @Schema(description = "来源地址", example = "https://www.iocoder.cn")
    private String url;

}