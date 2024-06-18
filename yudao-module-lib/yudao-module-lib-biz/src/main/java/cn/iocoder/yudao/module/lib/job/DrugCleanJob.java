package cn.iocoder.yudao.module.lib.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.lib.service.drug.DrugInfoService;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author qiuhongyun
 * @date 2024-06-18 13:03
 */
@Component
public class DrugCleanJob implements JobHandler {
    @Resource
    private DrugInfoService drugInfoService;

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
        drugInfoService.cleanDrugInfo();
        drugMarkingService.cleanDrugMarking();
        return "清理药品信息完成";
    }
}
