package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.system.enums.common.SexEnum;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
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

import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.service.pharmacydrug.PharmacyDrugService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 药房药品")
@RestController
@RequestMapping("/lib/pharmacy-drug")
@Validated
public class PharmacyDrugController {

    @Resource
    private PharmacyDrugService pharmacyDrugService;

    @PostMapping("/create")
    @Operation(summary = "创建药房药品")
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:create')")
    public CommonResult<Long> createPharmacyDrug(@Valid @RequestBody PharmacyDrugSaveReqVO createReqVO) {
        return success(pharmacyDrugService.createPharmacyDrug(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新药房药品")
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:update')")
    public CommonResult<Boolean> updatePharmacyDrug(@Valid @RequestBody PharmacyDrugSaveReqVO updateReqVO) {
        pharmacyDrugService.updatePharmacyDrug(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除药房药品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:delete')")
    public CommonResult<Boolean> deletePharmacyDrug(@RequestParam("id") Long id) {
        pharmacyDrugService.deletePharmacyDrug(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得药房药品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:query')")
    public CommonResult<PharmacyDrugRespVO> getPharmacyDrug(@RequestParam("id") Long id) {
        PharmacyDrugDO pharmacyDrug = pharmacyDrugService.getPharmacyDrug(id);
        return success(BeanUtils.toBean(pharmacyDrug, PharmacyDrugRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药房药品分页")
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:query')")
    public CommonResult<PageResult<PharmacyDrugRespVO>> getPharmacyDrugPage(@Valid PharmacyDrugPageReqVO pageReqVO) {
        PageResult<PharmacyDrugDO> pageResult = pharmacyDrugService.getPharmacyDrugPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PharmacyDrugRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出药房药品 Excel")
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPharmacyDrugExcel(@Valid PharmacyDrugPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PharmacyDrugDO> list = pharmacyDrugService.getPharmacyDrugPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "药房药品.xls", "数据", PharmacyDrugRespVO.class,
                        BeanUtils.toBean(list, PharmacyDrugRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入药物模板")
    @PermitAll
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 输出
        ExcelUtils.write(response, "药品导入模板.xls", "药品列表", DrugImportExcelVO.class, null);
    }


    @PostMapping("/import")
    @Operation(summary = "导入药房药品")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:import')")
    public CommonResult<DrugImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<DrugImportExcelVO> list = ExcelUtils.read(file, DrugImportExcelVO.class);
        return success(pharmacyDrugService.importDrug(list));
    }
}