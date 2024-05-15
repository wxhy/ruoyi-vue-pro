package cn.iocoder.yudao.module.lib.service.drugmarking;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.lib.controller.admin.drugmarking.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugmarking.DrugMarkingDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 药房药物对标 Service 接口
 *
 * @author 芋道源码
 */
public interface DrugMarkingService {

    /**
     * 创建药房药物对标
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDrugMarking(@Valid DrugMarkingSaveReqVO createReqVO);

    /**
     * 更新药房药物对标
     *
     * @param updateReqVO 更新信息
     */
    void updateDrugMarking(@Valid DrugMarkingSaveReqVO updateReqVO);

    /**
     * 删除药房药物对标
     *
     * @param id 编号
     */
    void deleteDrugMarking(Long id);

    /**
     * 获得药房药物对标
     *
     * @param id 编号
     * @return 药房药物对标
     */
    DrugMarkingDO getDrugMarking(Long id);

    /**
     * 获得药房药物对标分页
     *
     * @param pageReqVO 分页查询
     * @return 药房药物对标分页
     */
    PageResult<DrugMarkingDO> getDrugMarkingPage(DrugMarkingPageReqVO pageReqVO);

}