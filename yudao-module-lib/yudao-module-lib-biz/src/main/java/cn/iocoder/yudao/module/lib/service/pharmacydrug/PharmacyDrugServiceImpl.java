package cn.iocoder.yudao.module.lib.service.pharmacydrug;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lib.dal.mysql.pharmacydrug.PharmacyDrugMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lib.enums.ErrorCodeConstants.*;

/**
 * 药房药品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PharmacyDrugServiceImpl implements PharmacyDrugService {

    @Resource
    private PharmacyDrugMapper pharmacyDrugMapper;

    @Override
    public Long createPharmacyDrug(PharmacyDrugSaveReqVO createReqVO) {
        // 插入
        PharmacyDrugDO pharmacyDrug = BeanUtils.toBean(createReqVO, PharmacyDrugDO.class);
        pharmacyDrugMapper.insert(pharmacyDrug);
        // 返回
        return pharmacyDrug.getId();
    }

    @Override
    public void updatePharmacyDrug(PharmacyDrugSaveReqVO updateReqVO) {
        // 校验存在
        validatePharmacyDrugExists(updateReqVO.getId());
        // 更新
        PharmacyDrugDO updateObj = BeanUtils.toBean(updateReqVO, PharmacyDrugDO.class);
        pharmacyDrugMapper.updateById(updateObj);
    }

    @Override
    public void deletePharmacyDrug(Long id) {
        // 校验存在
        validatePharmacyDrugExists(id);
        // 删除
        pharmacyDrugMapper.deleteById(id);
    }

    private void validatePharmacyDrugExists(Long id) {
        if (pharmacyDrugMapper.selectById(id) == null) {
            throw exception(PHARMACY_DRUG_NOT_EXISTS);
        }
    }

    @Override
    public PharmacyDrugDO getPharmacyDrug(Long id) {
        return pharmacyDrugMapper.selectById(id);
    }

    @Override
    public PageResult<PharmacyDrugDO> getPharmacyDrugPage(PharmacyDrugPageReqVO pageReqVO) {
        return pharmacyDrugMapper.selectPage(pageReqVO);
    }

}