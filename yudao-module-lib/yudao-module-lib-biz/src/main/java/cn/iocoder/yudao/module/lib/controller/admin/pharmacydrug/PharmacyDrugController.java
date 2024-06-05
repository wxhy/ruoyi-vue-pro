package cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo.DrugYfRespVO;
import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import cn.iocoder.yudao.module.lib.service.pharmacydrug.PharmacyDrugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 药房药品")
@RestController
@RequestMapping("/lib/pharmacy-drug")
@Validated
public class PharmacyDrugController {

    @Resource
    private PharmacyDrugService pharmacyDrugService;
    @Resource
    private DrugMarkingService drugMarkingService;

    @Resource
    private DrugYfService drugYfService;

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

    @DeleteMapping("/deleteBatch")
    @Operation(summary = "删除药房药品")
    @Parameter(name = "ids", description = "编号集合", required = true)
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:delete')")
    public CommonResult<Boolean> deleteBatch(@RequestParam("ids") List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return success(true);
        }
        pharmacyDrugService.deletePharmacyDrugBatch(ids);
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
        PageResult<PharmacyDrugRespVO> result = BeanUtils.toBean(pageResult, PharmacyDrugRespVO.class);
        for (PharmacyDrugRespVO pharmacyDrugRespVO : result.getList()) {
            pharmacyDrugRespVO.setOtherPrice(pharmacyDrugService.getDrugOtherUserSalePrice(pharmacyDrugRespVO.getId()));
            pharmacyDrugRespVO.setDrugInfos(drugMarkingService.getMarkingDrugInfo(pharmacyDrugRespVO.getId()));
        }
        return success(result);
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

    @GetMapping("/watch")
    @Operation(summary = "关注/取消关注药房药品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lib:pharmacy-drug:watch')")
    public CommonResult<Boolean> watchPharmacyDrug(@RequestParam("id") Long id) {
        return success(pharmacyDrugService.watchPharmacyDrug(id));
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