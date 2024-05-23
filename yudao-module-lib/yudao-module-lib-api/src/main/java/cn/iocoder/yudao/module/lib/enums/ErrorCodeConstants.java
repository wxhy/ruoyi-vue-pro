package cn.iocoder.yudao.module.lib.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * lib 系统，使用 1-003-000-000 段
 */
public interface ErrorCodeConstants {

    ErrorCode PHARMACY_DRUG_NOT_EXISTS = new ErrorCode(1_003_000_001, "药房药物不存在");

    ErrorCode DRUG_MARKING_NOT_EXISTS = new ErrorCode(1_003_000_002, "药房药物对标不存在");

    ErrorCode DRUG_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_003_000_003, "导入药品数据不能为空！");

    ErrorCode MEMBER_USER_NOT_EXISTS = new ErrorCode(1_003_000_004, "您还不是会员！");

    ErrorCode MEMBER_USER_EXPIRE = new ErrorCode(1_003_000_005, "会员已过期！");

    ErrorCode MEMBER_LEVEL_IS_EMPTY = new ErrorCode(1_003_000_006, "当前用户会员等级不足");

    ErrorCode WATCH_DRUG_FULL = new ErrorCode(1_003_000_007, "特别关注数量已达上限");

    ErrorCode DRUG_YF_NOT_EXISTS = new ErrorCode(1_003_000_008, "药品不存在");
}