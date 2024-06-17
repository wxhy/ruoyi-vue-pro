package cn.iocoder.yudao.module.lib.service.pharmacydrug;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.constant.CommonConstant;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.dal.mysql.pharmacydrug.PharmacyDrugMapper;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import cn.iocoder.yudao.module.system.api.level.LevelApi;
import cn.iocoder.yudao.module.system.api.level.dto.LevelDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.errorprone.annotations.concurrent.LazyInit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lib.enums.ErrorCodeConstants.*;

/**
 * 药房药品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PharmacyDrugServiceImpl extends ServiceImpl<PharmacyDrugMapper, PharmacyDrugDO> implements PharmacyDrugService {

    @Resource
    private PharmacyDrugMapper pharmacyDrugMapper;

    @Resource
    private Validator validator;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private LevelApi levelApi;

    @Resource
    @LazyInit
    private DrugMarkingService drugMarkingService;

    @Resource
    private DrugYfService drugYfService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPharmacyDrug(PharmacyDrugSaveReqVO createReqVO) {
        // 插入
        PharmacyDrugDO pharmacyDrug = BeanUtils.toBean(createReqVO, PharmacyDrugDO.class);
        pharmacyDrug.setUserId(SecurityFrameworkUtils.getLoginUserId());
        pharmacyDrugMapper.insert(pharmacyDrug);
        //定标
        drugMarkingService.markingDrug(pharmacyDrug.getId());
        // 返回
        return pharmacyDrug.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePharmacyDrug(PharmacyDrugSaveReqVO updateReqVO) {
        // 校验存在
        validatePharmacyDrugExists(updateReqVO.getId());
        // 更新
        PharmacyDrugDO updateObj = BeanUtils.toBean(updateReqVO, PharmacyDrugDO.class);
        pharmacyDrugMapper.updateById(updateObj);

        drugMarkingService.deleteDrugMarkingByDrugId(updateObj.getId());
        //定标
        drugMarkingService.markingDrug(updateReqVO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePharmacyDrug(Long id) {
        // 校验存在
        validatePharmacyDrugExists(id);
        // 删除
        pharmacyDrugMapper.deleteById(id);
        drugMarkingService.deleteDrugMarkingByDrugId(id);
    }

    /**
     * 删除药房药品
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePharmacyDrugBatch(List<Long> ids) {
        pharmacyDrugMapper.deleteBatchIds(ids);
        drugMarkingService.deleteDrugMarkingByDrugIds(ids);
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
                validator.validate(item, Default.class);

                PharmacyDrugDO pharmacyDrugDO = pharmacyDrugMapper.selectDrugInfo(item.getApprovalNumber(), item.getCommonName(),
                        item.getManufacturer(), loginUserId, item.getSpecifications());
                if (Objects.isNull(pharmacyDrugDO)) {
                    pharmacyDrugDO = BeanUtils.toBean(item, PharmacyDrugDO.class);
                    pharmacyDrugDO.setUserId(loginUserId);
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
                drugMarkingService.markingDrug(pharmacyDrugDO.getId());
                success++;
                index++;
            } catch (Exception e) {
                failure++;
                index++;
                log.error("导入部分失败，原因：{}", e);
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

    /**
     * 特别关注或关注取消
     *
     * @param id
     * @return
     */
    @Override
    public Boolean watchPharmacyDrug(Long id) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        AdminUserRespDTO user = adminUserApi.getUser(loginUserId);
        if (Objects.isNull(user)) {
            throw exception(MEMBER_USER_NOT_EXISTS);
        }

        // 查询该药品是否已关注
        PharmacyDrugDO pharmacyDrugDO = pharmacyDrugMapper.selectOne(new LambdaQueryWrapperX<PharmacyDrugDO>()
                .eq(PharmacyDrugDO::getId, id)
                .eq(PharmacyDrugDO::getUserId, loginUserId));
        if (Objects.isNull(pharmacyDrugDO)) {
            throw exception(PHARMACY_DRUG_NOT_EXISTS);
        }

        if (pharmacyDrugDO.getWatch().equals(CommonConstant.INTEGER_ONE)) {
            pharmacyDrugMapper.update(new PharmacyDrugDO(), new LambdaUpdateWrapper<PharmacyDrugDO>()
                    .eq(PharmacyDrugDO::getId, id)
                    .eq(PharmacyDrugDO::getUserId, loginUserId)
                    .set(PharmacyDrugDO::getWatch, CommonConstant.INTEGER_ZERO));
            return Boolean.TRUE;
        }


        LevelDTO memberLevel = levelApi.getLevel(user.getLevelId());
        if (Objects.isNull(memberLevel)) {
            throw exception(MEMBER_LEVEL_IS_EMPTY);
        }
        // 查询已关注数量
        Long watchCount = pharmacyDrugMapper.selectCount(new LambdaQueryWrapperX<PharmacyDrugDO>()
                .eq(PharmacyDrugDO::getUserId, loginUserId)
                .eq(PharmacyDrugDO::getWatch, CommonConstant.INTEGER_ONE));
        if (watchCount >= memberLevel.getWatchCount()) {
            throw exception(WATCH_DRUG_FULL);
        }
        pharmacyDrugMapper.update(new PharmacyDrugDO(), new LambdaUpdateWrapper<PharmacyDrugDO>()
                .eq(PharmacyDrugDO::getId, id)
                .eq(PharmacyDrugDO::getUserId, loginUserId)
                .set(PharmacyDrugDO::getWatch, CommonConstant.INTEGER_ONE));
        return Boolean.TRUE;
    }

    /**
     * 获取药房药品在其他店家销售价格
     *
     * @param id
     * @return
     */
    @Override
    public BigDecimal getDrugOtherUserSalePrice(Long id) {
        List<DrugMarkingDO> markings = drugMarkingService.getDrugMarkingByPharmacyDrugId(id);
        if (CollUtil.isEmpty(markings)) {
            return null;
        }
        Set<Long> drugIds = CollectionUtils.convertSet(markings, DrugMarkingDO::getDataId);
        List<DrugYfDO> drugYfList = drugYfService.getDrugYfList(drugIds);
        drugYfList.sort(Comparator.comparing(DrugYfDO::getPrice));
        if (CollUtil.isEmpty(drugYfList)) {
            return null;
        }
        return CollectionUtils.getFirst(drugYfList).getPrice();
    }
}