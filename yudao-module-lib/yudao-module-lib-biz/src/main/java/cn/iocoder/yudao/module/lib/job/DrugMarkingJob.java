package cn.iocoder.yudao.module.lib.job;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import cn.iocoder.yudao.module.lib.service.pharmacydrug.PharmacyDrugService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qiuhongyun
 * @date 2024-06-18 12:58
 */
@Component
public class DrugMarkingJob implements JobHandler {

    @Resource
    private PharmacyDrugService pharmacyDrugService;

    @Resource
    private DrugMarkingService drugMarkingService;


    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public String execute(String param) throws Exception {
        List<PharmacyDrugDO> list = pharmacyDrugService.list(new LambdaQueryWrapperX<PharmacyDrugDO>()
                .in(PharmacyDrugDO::getStatus, 0, 2));
        for (PharmacyDrugDO pharmacyDrugDO : list) {
            drugMarkingService.markingDrug(pharmacyDrugDO.getId());
        }
        return "重新定标完成";
    }
}
