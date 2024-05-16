package cn.iocoder.yudao.module.lib.service.pharmacydrug;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 药房药品 Service 接口
 *
 * @author 芋道源码
 */
public interface PharmacyDrugService {

    /**
     * 创建药房药品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPharmacyDrug(@Valid PharmacyDrugSaveReqVO createReqVO);

    /**
     * 更新药房药品
     *
     * @param updateReqVO 更新信息
     */
    void updatePharmacyDrug(@Valid PharmacyDrugSaveReqVO updateReqVO);

    /**
     * 删除药房药品
     *
     * @param id 编号
     */
    void deletePharmacyDrug(Long id);

    /**
     * 获得药房药品
     *
     * @param id 编号
     * @return 药房药品
     */
    PharmacyDrugDO getPharmacyDrug(Long id);

    /**
     * 获得药房药品分页
     *
     * @param pageReqVO 分页查询
     * @return 药房药品分页
     */
    PageResult<PharmacyDrugDO> getPharmacyDrugPage(PharmacyDrugPageReqVO pageReqVO);

    /**
     * 数据导入
     * @param list
     * @return
     */
    DrugImportRespVO importDrug(List<DrugImportExcelVO> list);
}