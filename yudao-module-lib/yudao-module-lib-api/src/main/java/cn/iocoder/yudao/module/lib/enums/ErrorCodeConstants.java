package cn.iocoder.yudao.module.lib.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * lib 系统，使用 1-003-000-000 段
 */
public interface ErrorCodeConstants {

    ErrorCode PHARMACY_DRUG_NOT_EXISTS = new ErrorCode(1_003_000_001, "药房药物不存在");

    ErrorCode DRUG_MARKING_NOT_EXISTS = new ErrorCode(1_003_000_002, "药房药物对标不存在");

    ErrorCode DRUG_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_003_000_003, "导入药品数据不能为空！");
}