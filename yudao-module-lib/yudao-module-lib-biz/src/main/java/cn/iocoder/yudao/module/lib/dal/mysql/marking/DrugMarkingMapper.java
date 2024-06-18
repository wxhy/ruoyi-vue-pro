package cn.iocoder.yudao.module.lib.dal.mysql.marking;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lib.controller.admin.marking.vo.*;

/**
 * 药房药物对标 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DrugMarkingMapper extends BaseMapperX<DrugMarkingDO> {

    default PageResult<DrugMarkingDO> selectPage(DrugMarkingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DrugMarkingDO>()
                .eqIfPresent(DrugMarkingDO::getDataId, reqVO.getDataId())
                .eqIfPresent(DrugMarkingDO::getUserId, reqVO.getUserId())
                .eqIfPresent(DrugMarkingDO::getDrugId, reqVO.getDrugId())
                .betweenIfPresent(DrugMarkingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DrugMarkingDO::getId));
    }

    @Delete("delete from lib_drug_marking where deleted = 1")
    void cleanDrugMarking();

}