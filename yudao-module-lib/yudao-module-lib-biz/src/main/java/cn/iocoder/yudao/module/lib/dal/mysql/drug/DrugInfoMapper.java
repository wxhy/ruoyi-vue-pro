package cn.iocoder.yudao.module.lib.dal.mysql.drug;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lib.controller.admin.drug.vo.*;

/**
 * 药品爬虫 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DrugInfoMapper extends BaseMapperX<DrugInfoDO> {

    default PageResult<DrugInfoDO> selectPage(DrugInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DrugInfoDO>()
                .likeIfPresent(DrugInfoDO::getName, reqVO.getName())
                .eqIfPresent(DrugInfoDO::getApprovalNumber, reqVO.getApprovalNumber())
                .eqIfPresent(DrugInfoDO::getPacking, reqVO.getPacking())
                .eqIfPresent(DrugInfoDO::getDosageForm, reqVO.getDosageForm())
                .eqIfPresent(DrugInfoDO::getProductionEnterPrise, reqVO.getProductionEnterPrise())
                .eqIfPresent(DrugInfoDO::getPrice, reqVO.getPrice())
                .eqIfPresent(DrugInfoDO::getShopCount, reqVO.getShopCount())
                .eqIfPresent(DrugInfoDO::getImgInfo, reqVO.getImgInfo())
                .eqIfPresent(DrugInfoDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(DrugInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DrugInfoDO::getId));
    }

    @Delete("delete from drug_info where deleted = 1")
    void cleanDrugInfo();

}