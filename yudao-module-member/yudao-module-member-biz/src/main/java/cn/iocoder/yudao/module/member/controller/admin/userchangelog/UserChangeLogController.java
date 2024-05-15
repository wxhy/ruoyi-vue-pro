package cn.iocoder.yudao.module.member.controller.admin.userchangelog;

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

import cn.iocoder.yudao.module.member.controller.admin.userchangelog.vo.*;
import cn.iocoder.yudao.module.member.dal.dataobject.userchangelog.UserChangeLogDO;
import cn.iocoder.yudao.module.member.service.userchangelog.UserChangeLogService;

@Tag(name = "管理后台 - 会员变动记录")
@RestController
@RequestMapping("/member/user-change-log")
@Validated
public class UserChangeLogController {

    @Resource
    private UserChangeLogService userChangeLogService;

    @GetMapping("/page")
    @Operation(summary = "获得会员变动记录分页")
    @PreAuthorize("@ss.hasPermission('member:user-change-log:query')")
    public CommonResult<PageResult<UserChangeLogRespVO>> getUserChangeLogPage(@Valid UserChangeLogPageReqVO pageReqVO) {
        PageResult<UserChangeLogDO> pageResult = userChangeLogService.getUserChangeLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserChangeLogRespVO.class));
    }

}