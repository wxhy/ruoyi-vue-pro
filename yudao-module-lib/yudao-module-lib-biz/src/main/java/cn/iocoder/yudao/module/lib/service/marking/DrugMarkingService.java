package cn.iocoder.yudao.module.lib.service.marking;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.lib.controller.admin.marking.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 药房药物对标 Service 接口
 *
 * @author 芋道源码
 */
public interface DrugMarkingService extends IService<DrugMarkingDO> {

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
     * 删除药房药物对标
     *
     * @param drugIds 编号
     */
    void deleteDrugMarkingByDrugIds(List<Long> drugIds);

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

    /**
     * 获取药房药品对标
     * @param pharmacyDrugId
     * @return
     */
    List<DrugMarkingDO> getDrugMarkingByPharmacyDrugId(Long pharmacyDrugId);
    /**
     * 定标 药房药物
     * @param drugId
     */
    void markingDrug(Long drugId);


    void deleteDrugMarkingByDrugId(Long drugId);

    List<DrugMarkingRespVO> getMarkingDrugInfo(Long drugId);

}