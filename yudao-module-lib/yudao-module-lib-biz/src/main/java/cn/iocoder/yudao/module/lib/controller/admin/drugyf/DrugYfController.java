package cn.iocoder.yudao.module.lib.controller.admin.drugyf;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
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

import cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;

@Tag(name = "管理后台 - 药房药品")
@RestController
@RequestMapping("/lib/drug-yf")
@Validated
public class DrugYfController {

    @Resource
    private DrugYfService drugYfService;

    @Resource
    private DrugMarkingService drugMarkingService;

    @PostMapping("/create")
    @Operation(summary = "创建药房药品")
    @PreAuthorize("@ss.hasPermission('lib:drug-yf:create')")
    public CommonResult<Long> createDrugYf(@Valid @RequestBody DrugYfSaveReqVO createReqVO) {
        return success(drugYfService.createDrugYf(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新药房药品")
    @PreAuthorize("@ss.hasPermission('lib:drug-yf:update')")
    public CommonResult<Boolean> updateDrugYf(@Valid @RequestBody DrugYfSaveReqVO updateReqVO) {
        drugYfService.updateDrugYf(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除药房药品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lib:drug-yf:delete')")
    public CommonResult<Boolean> deleteDrugYf(@RequestParam("id") Long id) {
        drugYfService.deleteDrugYf(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得药房药品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lib:drug-yf:query')")
    public CommonResult<DrugYfRespVO> getDrugYf(@RequestParam("id") Long id) {
        DrugYfDO drugYf = drugYfService.getDrugYf(id);
        return success(BeanUtils.toBean(drugYf, DrugYfRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药房药品分页")
    @PreAuthorize("@ss.hasPermission('lib:drug-yf:query')")
    public CommonResult<PageResult<DrugYfRespVO>> getDrugYfPage(@Valid DrugYfPageReqVO pageReqVO) {
        PageResult<DrugYfDO> pageResult = drugYfService.getDrugYfPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DrugYfRespVO.class));
    }

    @GetMapping("/marking")
    @Operation(summary = "获得对标药房药品分页")
    public CommonResult<PageResult<DrugYfRespVO>> getMarkingDrugYfPage(@Valid DrugYfPageReqVO pageReqVO) {
        List<DrugMarkingDO> markingDOList = drugMarkingService.getDrugMarkingByPharmacyDrugId(pageReqVO.getPharmacyDrugId());
        List<Long> drugIds = CollectionUtils.convertList(markingDOList, DrugMarkingDO::getDataId);
        if (CollUtil.isEmpty(drugIds)) {
            return success(PageResult.empty());
        }
        pageReqVO.setDrugIds(drugIds);
        PageResult<DrugYfDO> pageResult = drugYfService.getDrugYfPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DrugYfRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出药房药品 Excel")
    @PreAuthorize("@ss.hasPermission('lib:drug-yf:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDrugYfExcel(@Valid DrugYfPageReqVO pageReqVO,
                                  HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DrugYfDO> list = drugYfService.getDrugYfPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "药房药品.xls", "数据", DrugYfRespVO.class,
                BeanUtils.toBean(list, DrugYfRespVO.class));
    }

}