package cn.iocoder.yudao.module.lib.service.pharmacydrug;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

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
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;

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
        pharmacyDrug.setUserId(SecurityFrameworkUtils.getLoginUserId());
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

    /**
     * 数据导入
     *
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrugImportRespVO importDrug(List<DrugImportExcelVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw exception(DRUG_IMPORT_LIST_IS_EMPTY);
        }
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        DrugImportRespVO drugImportRespVO = new DrugImportRespVO();
        drugImportRespVO.setTotalCount((long) list.size());
        long success = 0L;
        long failure = 0L;
        int index = 1;
        List<DrugImportDataInfo> dataInfos = new ArrayList<>();
        for (DrugImportExcelVO item : list) {
            DrugImportDataInfo dataInfo = new DrugImportDataInfo();
            dataInfo.setId(index);
            dataInfo.setApprovalNumber(item.getApprovalNumber());
            dataInfo.setDrugName(item.getCommonName());

            try {
                ValidationUtils.validate(item, DrugImportExcelVO.class);

                PharmacyDrugDO pharmacyDrugDO = pharmacyDrugMapper.selectDrugInfo(item.getApprovalNumber(), item.getCommonName(),
                        item.getManufacturer(), loginUserId, item.getSpecifications());
                if (Objects.isNull(pharmacyDrugDO)) {
                    pharmacyDrugDO = BeanUtils.toBean(item, PharmacyDrugDO.class);
                    pharmacyDrugMapper.insert(pharmacyDrugDO);
                    dataInfo.setStatus(1);
                    dataInfo.setReason("导入成功");
                } else {
                    PharmacyDrugDO bean = BeanUtils.toBean(item, PharmacyDrugDO.class);
                    bean.setId(pharmacyDrugDO.getId());
                    pharmacyDrugMapper.updateById(bean);
                    dataInfo.setStatus(2);
                    dataInfo.setReason("更新成功");
                }
                success++;
                index++;
            } catch (Exception e) {
                failure++;
                index++;
                dataInfo.setStatus(0);
                dataInfo.setReason(e.getMessage());
            }
            dataInfos.add(dataInfo);
        }
        drugImportRespVO.setSuccessCount(success);
        drugImportRespVO.setFailureCount(failure);
        drugImportRespVO.setDataInfos(dataInfos);
        return drugImportRespVO;
    }
}