package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import lombok.Data;

import java.util.List;

/**
 * @author qiuhongyun
 * @date 2024-05-16 15:15
 */
@Data
public class DrugImportRespVO {

    private Long totalCount;

    private Long successCount;

    private Long failureCount;


    private List<DrugImportDataInfo> dataInfos;
}
