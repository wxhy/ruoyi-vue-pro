package cn.iocoder.yudao.module.lib.job;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.constant.CommonConstant;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.service.drug.DrugInfoService;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import cn.iocoder.yudao.module.lib.service.pharmacydrug.PharmacyDrugService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DrugJob implements JobHandler {

    private static final Logger log = LoggerFactory.getLogger(DrugJob.class);
    @Resource
    private DrugInfoService drugInfoService;

    @Resource
    private DrugMarkingService drugMarkingService;

    @Resource
    private PharmacyDrugService pharmacyDrugService;

    @Resource
    private DrugYfService drugYfService;

    @Resource
    private AdminUserApi adminUserApi;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String execute(String param) {
        // 获取爬取的所有药品信息
        List<DrugInfoDO> drugInfos = drugInfoService.list();
        if (CollUtil.isEmpty(drugInfos)) {
            return "暂无爬取药品";
        }

        List<Long> drugIds = new ArrayList<>();
        for (DrugInfoDO drugInfo : drugInfos) {
            try {
                DrugYfDO drugYfDO = drugYfService.getDrugYfByDataId(drugInfo.getDataId());
                if (Objects.isNull(drugYfDO)) {
                    drugYfDO = new DrugYfDO();
                    drugYfDO.setName(drugInfo.getName());
                    drugYfDO.setApprovalNumber(drugInfo.getApprovalNumber());
                    drugYfDO.setPacking(drugInfo.getPacking());
                    drugYfDO.setDataId(drugInfo.getDataId());
                    drugYfDO.setDosageForm(drugInfo.getDosageForm());
                    drugYfDO.setPrice(new BigDecimal(drugInfo.getPrice()));
                    drugYfDO.setProductionEnterPrise(drugInfo.getProductionEnterPrise());
                    drugYfDO.setShopCount(drugInfo.getShopCount());
                    drugYfDO.setImgInfo(drugInfo.getImgInfo());
                    drugYfDO.setUrl(drugInfo.getUrl());
                    drugYfService.save(drugYfDO);
                } else {
                    BigDecimal bigDecimal = new BigDecimal(drugInfo.getPrice());
                    drugYfDO.setPrice(bigDecimal);
                    drugYfService.updateById(drugYfDO);
                    if (bigDecimal.compareTo(drugYfDO.getPrice()) != 0) {
                        drugIds.add(drugYfDO.getId());
                    }
                }
                drugInfoService.deleteDrugInfo(drugInfo.getId());
            } catch (Exception e) {
                log.error("更新药品价格报错：{}", e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

        }

        if (CollUtil.isEmpty(drugIds)) {
            return "暂无价格变动药品";
        }

        // 获取所有已关注的药品信息
        List<PharmacyDrugDO> watchList = pharmacyDrugService.list(new LambdaQueryWrapperX<PharmacyDrugDO>()
                .eq(PharmacyDrugDO::getWatch, CommonConstant.INTEGER_ONE));
        Set<Long> watchDrugIds = CollectionUtils.convertSet(watchList, PharmacyDrugDO::getId);
        if (CollUtil.isEmpty(watchDrugIds)) {
            return "暂无会员关注药品";
        }

        List<DrugMarkingDO> list = drugMarkingService.list(new LambdaQueryWrapperX<DrugMarkingDO>()
                .in(DrugMarkingDO::getDrugId, watchDrugIds));
        Map<Long, DrugMarkingDO> markingDOMap = CollectionUtils.convertMap(list, DrugMarkingDO::getDataId);
        Set<Long> dataIds = CollectionUtils.convertSet(list, DrugMarkingDO::getDataId);
        Collection<Long> intersection = CollUtil.intersection(dataIds, drugIds);
        if (CollUtil.isEmpty(intersection)) {
            return "暂无关注的药品价格变动";
        }
        List<Long> userIds = intersection.stream().map(item -> {
            DrugMarkingDO drugMarkingDO = markingDOMap.get(item);
            if (Objects.nonNull(drugMarkingDO)) {
                return drugMarkingDO.getUserId();
            }
            return null;
        }).collect(Collectors.toList());

        // 给用户发送短信提醒
//        adminUserApi.sendMessage(userIds);

        return "同步药品价格变动完成";
    }

}
