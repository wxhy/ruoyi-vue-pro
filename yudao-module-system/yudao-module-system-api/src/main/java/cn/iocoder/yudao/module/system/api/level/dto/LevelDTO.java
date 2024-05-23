package cn.iocoder.yudao.module.system.api.level.dto;

import lombok.Data;

/**
 * @author qiuhongyun
 * @date 2024-05-23 15:11
 */
@Data
public class LevelDTO {

    private Long id;

    private String name;

    private Integer watchCount;

    private Integer sort;

    private String icon;

    private Integer status;

    private String remark;
}
