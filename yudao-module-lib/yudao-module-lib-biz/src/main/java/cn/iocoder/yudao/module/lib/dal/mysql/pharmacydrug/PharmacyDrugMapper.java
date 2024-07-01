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
                .eqIfPresent(PharmacyDrugDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PharmacyDrugDO::getWatch, reqVO.getWatch())
                .eqIfPresent(PharmacyDrugDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PharmacyDrugDO::getCreateTime, reqVO.getCreateTime())
                .last(" ORDER BY watch DESC, FIELD(status, 1, 0, 2)"));
    }

    /**
     * 获取药品信息
     *
     * @param approvalNumber
     * @param commonName
     * @param manufacturer
     * @param userId
     * @param specifications
     * @return
     */
    default PharmacyDrugDO selectDrugInfo(String approvalNumber, String commonName, String manufacturer,
                                          Long userId, String specifications) {
        return selectOne(new LambdaQueryWrapperX<PharmacyDrugDO>()
                .eq(PharmacyDrugDO::getApprovalNumber, approvalNumber)
                .eq(PharmacyDrugDO::getCommonName, commonName)
                .eq(PharmacyDrugDO::getManufacturer, manufacturer)
                .eq(PharmacyDrugDO::getUserId, userId)
                .eq(PharmacyDrugDO::getSpecifications, specifications));
    }
}