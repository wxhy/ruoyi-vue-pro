package cn.iocoder.yudao.module.lib.service.drugyf;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 药房药品 Service 接口
 *
 * @author 芋道源码
 */
public interface DrugYfService {

    /**
     * 创建药房药品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDrugYf(@Valid DrugYfSaveReqVO createReqVO);

    /**
     * 更新药房药品
     *
     * @param updateReqVO 更新信息
     */
    void updateDrugYf(@Valid DrugYfSaveReqVO updateReqVO);

    /**
     * 删除药房药品
     *
     * @param id 编号
     */
    void deleteDrugYf(Long id);

    /**
     * 获得药房药品
     *
     * @param id 编号
     * @return 药房药品
     */
    DrugYfDO getDrugYf(Long id);

    /**
     * 获得药房药品分页
     *
     * @param pageReqVO 分页查询
     * @return 药房药品分页
     */
    PageResult<DrugYfDO> getDrugYfPage(DrugYfPageReqVO pageReqVO);


    /**
     * 获得药房药品列表
     *
     * @return 药房药品列表
     */
    List<DrugYfDO> getDrugYfList(String drugName, String approvalNumber, String packing,
                                 String dosageForm, String productionEnterPrise);

}