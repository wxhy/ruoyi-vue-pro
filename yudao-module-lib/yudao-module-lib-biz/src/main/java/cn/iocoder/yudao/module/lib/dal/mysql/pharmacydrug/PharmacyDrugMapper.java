package cn.iocoder.yudao.module.lib.dal.mysql.pharmacydrug;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;

/**
 * 药房药品 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PharmacyDrugMapper extends BaseMapperX<PharmacyDrugDO> {

    default PageResult<PharmacyDrugDO> selectPage(PharmacyDrugPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PharmacyDrugDO>()
                .likeIfPresent(PharmacyDrugDO::getApprovalNumber, reqVO.getApprovalNumber())
                .likeIfPresent(PharmacyDrugDO::getCommonName, reqVO.getCommonName())
                .likeIfPresent(PharmacyDrugDO::getManufacturer, reqVO.getManufacturer())
                .likeIfPresent(PharmacyDrugDO::getProductBatch, reqVO.getProductBatch())
                .eqIfPresent(PharmacyDrugDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PharmacyDrugDO::getWatch, reqVO.getWatch())
                .betweenIfPresent(PharmacyDrugDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PharmacyDrugDO::getId));
    }

}