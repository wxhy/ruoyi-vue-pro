package cn.iocoder.yudao.module.lib.service.drugmarking;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lib.controller.admin.drugmarking.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugmarking.DrugMarkingDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lib.dal.mysql.drugmarking.DrugMarkingMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lib.enums.ErrorCodeConstants.*;

/**
 * 药房药物对标 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DrugMarkingServiceImpl implements DrugMarkingService {

    @Resource
    private DrugMarkingMapper drugMarkingMapper;

    @Override
    public Long createDrugMarking(DrugMarkingSaveReqVO createReqVO) {
        // 插入
        DrugMarkingDO drugMarking = BeanUtils.toBean(createReqVO, DrugMarkingDO.class);
        drugMarkingMapper.insert(drugMarking);
        // 返回
        return drugMarking.getId();
    }

    @Override
    public void updateDrugMarking(DrugMarkingSaveReqVO updateReqVO) {
        // 校验存在
        validateDrugMarkingExists(updateReqVO.getId());
        // 更新
        DrugMarkingDO updateObj = BeanUtils.toBean(updateReqVO, DrugMarkingDO.class);
        drugMarkingMapper.updateById(updateObj);
    }

    @Override
    public void deleteDrugMarking(Long id) {
        // 校验存在
        validateDrugMarkingExists(id);
        // 删除
        drugMarkingMapper.deleteById(id);
    }

    private void validateDrugMarkingExists(Long id) {
        if (drugMarkingMapper.selectById(id) == null) {
            throw exception(DRUG_MARKING_NOT_EXISTS);
        }
    }

    @Override
    public DrugMarkingDO getDrugMarking(Long id) {
        return drugMarkingMapper.selectById(id);
    }

    @Override
    public PageResult<DrugMarkingDO> getDrugMarkingPage(DrugMarkingPageReqVO pageReqVO) {
        return drugMarkingMapper.selectPage(pageReqVO);
    }

}