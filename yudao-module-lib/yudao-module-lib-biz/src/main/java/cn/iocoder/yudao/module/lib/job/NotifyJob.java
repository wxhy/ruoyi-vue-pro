package cn.iocoder.yudao.module.lib.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.constant.CommonConstant;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.marking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.service.drugyf.DrugYfService;
import cn.iocoder.yudao.module.lib.service.marking.DrugMarkingService;
import cn.iocoder.yudao.module.lib.service.pharmacydrug.PharmacyDrugService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

public class NotifyJob implements JobHandler {


    @Resource
    private DrugMarkingService drugMarkingService;

    @Resource
    private PharmacyDrugService pharmacyDrugService;

    @Resource
    private DrugYfService drugYfService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String NOTIFY_LAST_TIME = "notify:last";

    @Override
    public String execute(String param) throws Exception {

        List<AdminUserRespDTO> users = adminUserApi.getUserListByMemberLevel(CollUtil.newArrayList(2L, 3L));
        Set<Long> userIds = CollectionUtils.convertSet(users, AdminUserRespDTO::getId);
        if (CollUtil.isEmpty(userIds)) {
            return "暂无合适的会员需要提醒";
        }
        // 获取所有已关注的药品信息
        List<PharmacyDrugDO> watchList = pharmacyDrugService.list(new LambdaQueryWrapperX<PharmacyDrugDO>()
                .eq(PharmacyDrugDO::getWatch, CommonConstant.INTEGER_ONE)
                .in(PharmacyDrugDO::getUserId, userIds));
        Set<Long> watchDrugIds = CollectionUtils.convertSet(watchList, PharmacyDrugDO::getId);
        if (CollUtil.isEmpty(watchDrugIds)) {
            stringRedisTemplate.opsForValue().set(NOTIFY_LAST_TIME, DateUtil.format(new Date(), NORM_DATETIME_PATTERN));
            return "暂无会员关注药品";
        }

        String lastNotify = stringRedisTemplate.opsForValue().get(NOTIFY_LAST_TIME);

        if (StrUtil.isBlank(lastNotify)) {
            stringRedisTemplate.opsForValue().set(NOTIFY_LAST_TIME, DateUtil.format(new Date(), NORM_DATETIME_PATTERN));
            return "完成";
        }
        List<DrugMarkingDO> list = drugMarkingService.list(new LambdaQueryWrapperX<DrugMarkingDO>()
                .in(DrugMarkingDO::getDrugId, watchDrugIds));
        Map<Long, DrugMarkingDO> markingDOMap = CollectionUtils.convertMap(list, DrugMarkingDO::getDataId);
        Set<Long> dataIds = CollectionUtils.convertSet(list, DrugMarkingDO::getDataId);
        // 所有关注的药品价格
        List<DrugYfDO> drugYfList = drugYfService.getDrugYfList(dataIds, lastNotify);
        if (CollUtil.isEmpty(drugYfList)) {
            stringRedisTemplate.opsForValue().set(NOTIFY_LAST_TIME, DateUtil.format(new Date(), NORM_DATETIME_PATTERN));
            return "暂无药品价格变动";
        }

        List<Long> memberUserIds = drugYfList.stream().map(item -> {
            DrugMarkingDO drugMarkingDO = markingDOMap.get(item.getId());
            if (Objects.nonNull(drugMarkingDO)) {
                return drugMarkingDO.getUserId();
            }
            return null;
        }).collect(Collectors.toList());

        // 给用户发送短信提醒
        LocalTime currentTime = LocalTime.now();
        boolean isBetween = currentTime.isAfter(LocalTime.of(7, 50)) && currentTime.isBefore(LocalTime.of(20, 10));
        if (isBetween) {
            adminUserApi.sendMessage(memberUserIds);
        }
        stringRedisTemplate.opsForValue().set(NOTIFY_LAST_TIME, DateUtil.format(new Date(), NORM_DATETIME_PATTERN));

        return "药品价格处理完成";
    }
}
