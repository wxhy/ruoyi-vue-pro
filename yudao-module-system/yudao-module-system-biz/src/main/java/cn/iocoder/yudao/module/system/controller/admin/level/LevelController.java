package cn.iocoder.yudao.module.system.controller.admin.level;

import ch.qos.logback.classic.pattern.LevelConverter;
import cn.iocoder.yudao.module.system.convert.level.LevelConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
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

import cn.iocoder.yudao.module.system.controller.admin.level.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import cn.iocoder.yudao.module.system.service.level.LevelService;

@Tag(name = "管理后台 - 用户等级")
@RestController
@RequestMapping("/system/level")
@Validated
public class LevelController {

    @Resource
    private LevelService levelService;

    @PostMapping("/create")
    @Operation(summary = "创建用户等级")
    @PreAuthorize("@ss.hasPermission('system:level:create')")
    public CommonResult<Long> createLevel(@Valid @RequestBody LevelSaveReqVO createReqVO) {
        return success(levelService.createLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户等级")
    @PreAuthorize("@ss.hasPermission('system:level:update')")
    public CommonResult<Boolean> updateLevel(@Valid @RequestBody LevelSaveReqVO updateReqVO) {
        levelService.updateLevel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户等级")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:level:delete')")
    public CommonResult<Boolean> deleteLevel(@RequestParam("id") Long id) {
        levelService.deleteLevel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户等级")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:level:query')")
    public CommonResult<LevelRespVO> getLevel(@RequestParam("id") Long id) {
        LevelDO level = levelService.getLevel(id);
        return success(BeanUtils.toBean(level, LevelRespVO.class));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取会员等级精简信息列表", description = "只包含被开启的会员等级，主要用于前端的下拉选项")
    public CommonResult<List<LevelRespVO>> getSimpleLevelList() {
        // 获用户列表，只要开启状态的
        List<LevelDO> list = levelService.getEnableLevelList();
        // 排序后，返回给前端
        list.sort(Comparator.comparing(LevelDO::getSort));
        return success(LevelConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户等级分页")
    @PreAuthorize("@ss.hasPermission('system:level:query')")
    public CommonResult<PageResult<LevelRespVO>> getLevelPage(@Valid LevelPageReqVO pageReqVO) {
        PageResult<LevelDO> pageResult = levelService.getLevelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, LevelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户等级 Excel")
    @PreAuthorize("@ss.hasPermission('system:level:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportLevelExcel(@Valid LevelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LevelDO> list = levelService.getLevelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户等级.xls", "数据", LevelRespVO.class,
                        BeanUtils.toBean(list, LevelRespVO.class));
    }

}