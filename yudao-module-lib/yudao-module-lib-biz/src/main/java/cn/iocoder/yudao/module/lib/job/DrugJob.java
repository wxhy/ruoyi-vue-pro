package cn.iocoder.yudao.module.lib.job;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.service.drug.DrugInfoService;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Component
public class DrugJob implements JobHandler {

    private static final Logger log = LoggerFactory.getLogger(DrugJob.class);
    @Resource
    private DrugInfoService drugInfoService;

    @Resource
    private DrugYfService drugYfService;

    @Resource
    private DrugMarkingService drugMarkingService;

    @Override
    public String execute(String param) {
        // 获取爬取的所有药品信息
        List<DrugInfoDO> drugInfos = drugInfoService.list();
        if (CollUtil.isEmpty(drugInfos)) {
            return "暂无爬取药品";
        }

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
                    drugYfDO.setName(drugInfo.getName());
                    drugYfDO.setPacking(drugInfo.getPacking());
                    drugYfDO.setApprovalNumber(drugInfo.getApprovalNumber());
                    drugYfDO.setDosageForm(drugInfo.getDosageForm());
                    drugYfDO.setShopCount(drugInfo.getShopCount());
                    drugYfDO.setProductionEnterPrise(drugInfo.getProductionEnterPrise());
                    drugYfService.updateById(drugYfDO);

                    DrugMarkingDO drugMarkingDO = new DrugMarkingDO();
                    drugMarkingDO.setName(drugInfo.getName());
                    drugMarkingDO.setPacking(drugInfo.getPacking());
                    drugMarkingDO.setApprovalNumber(drugInfo.getApprovalNumber());
                    drugMarkingDO.setDosageForm(drugInfo.getDosageForm());
                    drugMarkingDO.setShopCount(drugInfo.getShopCount());
                    drugMarkingDO.setProductionEnterPrise(drugInfo.getProductionEnterPrise());
                    drugMarkingDO.setPrice(bigDecimal);
                    drugMarkingDO.setImgInfo(drugInfo.getImgInfo());
                    drugMarkingService.update(drugMarkingDO, new LambdaUpdateWrapper<DrugMarkingDO>()
                            .eq(DrugMarkingDO::getDataId, drugYfDO.getId()));

                }
                drugInfoService.deleteDrugInfo(drugInfo.getId());
            } catch (Exception e) {
                log.error("更新药品价格报错：{}", e.getMessage());
            }
        }
        return "同步药品价格变动完成";
    }

}
