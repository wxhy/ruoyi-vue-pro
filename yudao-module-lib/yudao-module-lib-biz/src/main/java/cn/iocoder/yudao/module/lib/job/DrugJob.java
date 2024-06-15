package cn.iocoder.yudao.module.lib.job;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.service.drug.DrugInfoService;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
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

    @Override
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
            }
        }
        return "同步药品价格变动完成";
    }

}
