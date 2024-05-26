package cn.iocoder.yudao.module.lib.dal.mysql.drugyf;

import java.util.*;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo.*;

/**
 * 药房药品 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DrugYfMapper extends BaseMapperX<DrugYfDO> {

    default PageResult<DrugYfDO> selectPage(DrugYfPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DrugYfDO>()
                .likeIfPresent(DrugYfDO::getName, reqVO.getName())
                .eqIfPresent(DrugYfDO::getApprovalNumber, reqVO.getApprovalNumber())
                .eqIfPresent(DrugYfDO::getPacking, reqVO.getPacking())
                .eqIfPresent(DrugYfDO::getDosageForm, reqVO.getDosageForm())
                .eqIfPresent(DrugYfDO::getProductionEnterPrise, reqVO.getProductionEnterPrise())
                .betweenIfPresent(DrugYfDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(DrugYfDO::getId, reqVO.getDrugIds())
                .orderByAsc(CollUtil.isNotEmpty(reqVO.getDrugIds()),DrugYfDO::getPrice)
                .orderByDesc(CollUtil.isEmpty(reqVO.getDrugIds()), DrugYfDO::getCreateTime));
    }

}