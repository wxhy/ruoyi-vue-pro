package cn.iocoder.yudao.module.lib.service.drug;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.lib.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 药品爬虫 Service 接口
 *
 * @author 芋道源码
 */
public interface DrugInfoService extends IService<DrugInfoDO> {

    /**
     * 创建药品爬虫
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createDrugInfo(@Valid DrugInfoSaveReqVO createReqVO);

    /**
     * 更新药品爬虫
     *
     * @param updateReqVO 更新信息
     */
    void updateDrugInfo(@Valid DrugInfoSaveReqVO updateReqVO);

    /**
     * 删除药品爬虫
     *
     * @param id 编号
     */
    void deleteDrugInfo(Integer id);

    /**
     * 获得药品爬虫
     *
     * @param id 编号
     * @return 药品爬虫
     */
    DrugInfoDO getDrugInfo(Integer id);

    /**
     * 获得药品爬虫分页
     *
     * @param pageReqVO 分页查询
     * @return 药品爬虫分页
     */
    PageResult<DrugInfoDO> getDrugInfoPage(DrugInfoPageReqVO pageReqVO);

}