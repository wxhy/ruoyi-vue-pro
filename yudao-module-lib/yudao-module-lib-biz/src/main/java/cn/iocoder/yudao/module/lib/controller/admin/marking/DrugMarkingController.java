package cn.iocoder.yudao.module.lib.controller.admin.marking;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.iocoder.yudao.module.lib.controller.admin.marking.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;

@Tag(name = "管理后台 - 药房药物对标")
@RestController
@RequestMapping("/lib/drug-marking")
@Validated
public class DrugMarkingController {

    @Resource
    private DrugMarkingService drugMarkingService;

    @PostMapping("/create")
    @Operation(summary = "创建药房药物对标")
    @PreAuthorize("@ss.hasPermission('lib:drug-marking:create')")
    public CommonResult<Long> createDrugMarking(@Valid @RequestBody DrugMarkingSaveReqVO createReqVO) {
        return success(drugMarkingService.createDrugMarking(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新药房药物对标")
    @PreAuthorize("@ss.hasPermission('lib:drug-marking:update')")
    public CommonResult<Boolean> updateDrugMarking(@Valid @RequestBody DrugMarkingSaveReqVO updateReqVO) {
        drugMarkingService.updateDrugMarking(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除药房药物对标")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lib:drug-marking:delete')")
    public CommonResult<Boolean> deleteDrugMarking(@RequestParam("id") Long id) {
        drugMarkingService.deleteDrugMarking(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得药房药物对标")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lib:drug-marking:query')")
    public CommonResult<DrugMarkingRespVO> getDrugMarking(@RequestParam("id") Long id) {
        DrugMarkingDO drugMarking = drugMarkingService.getDrugMarking(id);
        return success(BeanUtils.toBean(drugMarking, DrugMarkingRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药房药物对标分页")
    @PreAuthorize("@ss.hasPermission('lib:drug-marking:query')")
    public CommonResult<PageResult<DrugMarkingRespVO>> getDrugMarkingPage(@Valid DrugMarkingPageReqVO pageReqVO) {
        PageResult<DrugMarkingDO> pageResult = drugMarkingService.getDrugMarkingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DrugMarkingRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出药房药物对标 Excel")
    @PreAuthorize("@ss.hasPermission('lib:drug-marking:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDrugMarkingExcel(@Valid DrugMarkingPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DrugMarkingDO> list = drugMarkingService.getDrugMarkingPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "药房药物对标.xls", "数据", DrugMarkingRespVO.class,
                        BeanUtils.toBean(list, DrugMarkingRespVO.class));
    }

}