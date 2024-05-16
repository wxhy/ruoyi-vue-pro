package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo;

import lombok.Data;

/**
 * @author qiuhongyun
 * @date 2024-05-16 15:20
 */
@Data
public class DrugImportDataInfo {

    private Integer id;

    private String approvalNumber;

    private String drugName;

    private Integer status;

    private String reason;
}
