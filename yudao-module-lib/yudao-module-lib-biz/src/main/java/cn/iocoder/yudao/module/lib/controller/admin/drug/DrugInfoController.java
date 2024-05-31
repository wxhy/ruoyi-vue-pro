package cn.iocoder.yudao.module.lib.controller.admin.drug;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.lib.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import cn.iocoder.yudao.module.lib.service.drug.DrugInfoService;

@Tag(name = "管理后台 - 药品爬虫")
@RestController
@RequestMapping("/lib/drug-info")
@Validated
public class DrugInfoController {

    @Resource
    private DrugInfoService drugInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建药品爬虫")
    @PreAuthorize("@ss.hasPermission('lib:drug-info:create')")
    public CommonResult<Integer> createDrugInfo(@Valid @RequestBody DrugInfoSaveReqVO createReqVO) {
        return success(drugInfoService.createDrugInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新药品爬虫")
    @PreAuthorize("@ss.hasPermission('lib:drug-info:update')")
    public CommonResult<Boolean> updateDrugInfo(@Valid @RequestBody DrugInfoSaveReqVO updateReqVO) {
        drugInfoService.updateDrugInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除药品爬虫")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lib:drug-info:delete')")
    public CommonResult<Boolean> deleteDrugInfo(@RequestParam("id") Integer id) {
        drugInfoService.deleteDrugInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得药品爬虫")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lib:drug-info:query')")
    public CommonResult<DrugInfoRespVO> getDrugInfo(@RequestParam("id") Integer id) {
        DrugInfoDO drugInfo = drugInfoService.getDrugInfo(id);
        return success(BeanUtils.toBean(drugInfo, DrugInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药品爬虫分页")
    @PreAuthorize("@ss.hasPermission('lib:drug-info:query')")
    public CommonResult<PageResult<DrugInfoRespVO>> getDrugInfoPage(@Valid DrugInfoPageReqVO pageReqVO) {
        PageResult<DrugInfoDO> pageResult = drugInfoService.getDrugInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DrugInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出药品爬虫 Excel")
    @PreAuthorize("@ss.hasPermission('lib:drug-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDrugInfoExcel(@Valid DrugInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DrugInfoDO> list = drugInfoService.getDrugInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "药品爬虫.xls", "数据", DrugInfoRespVO.class,
                        BeanUtils.toBean(list, DrugInfoRespVO.class));
    }

}