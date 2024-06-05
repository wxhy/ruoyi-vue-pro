package cn.iocoder.yudao.module.lib.service.marking;
import java.math.BigDecimal;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
import cn.iocoder.yudao.module.lib.service.pharmacydrug.PharmacyDrugService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lib.controller.admin.marking.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lib.dal.mysql.marking.DrugMarkingMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lib.enums.ErrorCodeConstants.*;

/**
 * 药房药物对标 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DrugMarkingServiceImpl extends ServiceImpl<DrugMarkingMapper, DrugMarkingDO> implements DrugMarkingService {

    @Resource
    private DrugMarkingMapper drugMarkingMapper;

    @Resource
    @Lazy
    private PharmacyDrugService pharmacyDrugService;

    @Resource
    private DrugYfService drugYfService;

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

    /**
     * 删除药房药物对标
     *
     * @param drugIds 编号
     */
    @Override
    public void deleteDrugMarkingByDrugIds(List<Long> drugIds) {
        drugMarkingMapper.delete(new LambdaQueryWrapperX<DrugMarkingDO>()
                .in(DrugMarkingDO::getDrugId, drugIds));
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

    @Override
    public List<DrugMarkingDO> getDrugMarkingByPharmacyDrugId(Long pharmacyDrugId) {
        return drugMarkingMapper.selectList(DrugMarkingDO::getDrugId, pharmacyDrugId);
    }

    /**
     * 定标 药房药物
     *
     * @param drugId
     */
    @Override
    public void markingDrug(Long drugId) {
        PharmacyDrugDO pharmacyDrug = pharmacyDrugService.getPharmacyDrug(drugId);
        if (Objects.isNull(pharmacyDrug)) {
            return;
        }
        List<DrugYfDO> drugYfList = drugYfService.getDrugYfList(pharmacyDrug.getCommonName());
        if (CollUtil.isEmpty(drugYfList)) {
            return;
        }
        // 查询现在对标的
        List<DrugMarkingDO> drugMarkingDOS = drugMarkingMapper.selectList(DrugMarkingDO::getDrugId, drugId);
        Set<Long> dataIds = CollectionUtils.convertSet(drugMarkingDOS, DrugMarkingDO::getDataId);
        List<DrugMarkingDO> drugMarkingList = new ArrayList<>();
        for (DrugYfDO drugYfDO : drugYfList) {
            if (!dataIds.contains(drugYfDO.getId())) {
                DrugMarkingDO drugMarkingDO = new DrugMarkingDO();
                drugMarkingDO.setDrugId(drugId);
                drugMarkingDO.setDataId(drugYfDO.getId());
                drugMarkingDO.setUserId(pharmacyDrug.getUserId());
                drugMarkingList.add(drugMarkingDO);
            }
        }

        if (CollUtil.isNotEmpty(drugMarkingList)) {
            drugMarkingMapper.insertBatch(drugMarkingList);
        }
    }

    @Override
    public void deleteDrugMarkingByDrugId(Long drugId) {
        drugMarkingMapper.delete(DrugMarkingDO::getDrugId, drugId);
    }

    @Override
    public List<MarkingDrugInfo> getMarkingDrugInfo(Long drugId) {
        List<DrugMarkingDO> drugMarkingDOS = drugMarkingMapper.selectList(DrugMarkingDO::getDrugId, drugId);
        if (CollUtil.isEmpty(drugMarkingDOS)) {
            return Collections.emptyList();
        }
        Set<Long> dataIds = CollectionUtils.convertSet(drugMarkingDOS, DrugMarkingDO::getDataId);
        List<DrugYfDO> drugYfList = drugYfService.getDrugYfList(dataIds);
        Map<Long, DrugYfDO> drugYfDOMap = CollectionUtils.convertMap(drugYfList, DrugYfDO::getId);
        List<MarkingDrugInfo> result = new ArrayList<>();
        for (DrugMarkingDO drugMarkingDO : drugMarkingDOS) {
            DrugYfDO drugYfDO = drugYfDOMap.get(drugMarkingDO.getDataId());
            if (Objects.nonNull(drugYfDO)) {
                MarkingDrugInfo markingDrugInfo = new MarkingDrugInfo();
                markingDrugInfo.setId(drugMarkingDO.getId());
                markingDrugInfo.setDataId(drugMarkingDO.getDataId());
                markingDrugInfo.setName(drugYfDO.getName());
                markingDrugInfo.setApprovalNumber(drugYfDO.getApprovalNumber());
                markingDrugInfo.setPacking(drugYfDO.getPacking());
                markingDrugInfo.setDosageForm(drugYfDO.getDosageForm());
                markingDrugInfo.setProductionEnterPrise(drugYfDO.getProductionEnterPrise());
                markingDrugInfo.setPrice(drugYfDO.getPrice());
                markingDrugInfo.setShopCount(drugYfDO.getShopCount());
                result.add(markingDrugInfo);
            }
        }
        return result;
    }
}