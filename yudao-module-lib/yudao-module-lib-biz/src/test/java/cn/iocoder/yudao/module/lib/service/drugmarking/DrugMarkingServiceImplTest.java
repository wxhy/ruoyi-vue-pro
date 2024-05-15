package cn.iocoder.yudao.module.lib.service.drugmarking;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.lib.controller.admin.drugmarking.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugmarking.DrugMarkingDO;
import cn.iocoder.yudao.module.lib.dal.mysql.drugmarking.DrugMarkingMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.lib.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link DrugMarkingServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(DrugMarkingServiceImpl.class)
public class DrugMarkingServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DrugMarkingServiceImpl drugMarkingService;

    @Resource
    private DrugMarkingMapper drugMarkingMapper;

    @Test
    public void testCreateDrugMarking_success() {
        // 准备参数
        DrugMarkingSaveReqVO createReqVO = randomPojo(DrugMarkingSaveReqVO.class).setId(null);

        // 调用
        Long drugMarkingId = drugMarkingService.createDrugMarking(createReqVO);
        // 断言
        assertNotNull(drugMarkingId);
        // 校验记录的属性是否正确
        DrugMarkingDO drugMarking = drugMarkingMapper.selectById(drugMarkingId);
        assertPojoEquals(createReqVO, drugMarking, "id");
    }

    @Test
    public void testUpdateDrugMarking_success() {
        // mock 数据
        DrugMarkingDO dbDrugMarking = randomPojo(DrugMarkingDO.class);
        drugMarkingMapper.insert(dbDrugMarking);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DrugMarkingSaveReqVO updateReqVO = randomPojo(DrugMarkingSaveReqVO.class, o -> {
            o.setId(dbDrugMarking.getId()); // 设置更新的 ID
        });

        // 调用
        drugMarkingService.updateDrugMarking(updateReqVO);
        // 校验是否更新正确
        DrugMarkingDO drugMarking = drugMarkingMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, drugMarking);
    }

    @Test
    public void testUpdateDrugMarking_notExists() {
        // 准备参数
        DrugMarkingSaveReqVO updateReqVO = randomPojo(DrugMarkingSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> drugMarkingService.updateDrugMarking(updateReqVO), DRUG_MARKING_NOT_EXISTS);
    }

    @Test
    public void testDeleteDrugMarking_success() {
        // mock 数据
        DrugMarkingDO dbDrugMarking = randomPojo(DrugMarkingDO.class);
        drugMarkingMapper.insert(dbDrugMarking);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDrugMarking.getId();

        // 调用
        drugMarkingService.deleteDrugMarking(id);
       // 校验数据不存在了
       assertNull(drugMarkingMapper.selectById(id));
    }

    @Test
    public void testDeleteDrugMarking_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> drugMarkingService.deleteDrugMarking(id), DRUG_MARKING_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDrugMarkingPage() {
       // mock 数据
       DrugMarkingDO dbDrugMarking = randomPojo(DrugMarkingDO.class, o -> { // 等会查询到
           o.setDataId(null);
           o.setUserId(null);
           o.setDrugId(null);
           o.setCreateTime(null);
       });
       drugMarkingMapper.insert(dbDrugMarking);
       // 测试 dataId 不匹配
       drugMarkingMapper.insert(cloneIgnoreId(dbDrugMarking, o -> o.setDataId(null)));
       // 测试 userId 不匹配
       drugMarkingMapper.insert(cloneIgnoreId(dbDrugMarking, o -> o.setUserId(null)));
       // 测试 drugId 不匹配
       drugMarkingMapper.insert(cloneIgnoreId(dbDrugMarking, o -> o.setDrugId(null)));
       // 测试 createTime 不匹配
       drugMarkingMapper.insert(cloneIgnoreId(dbDrugMarking, o -> o.setCreateTime(null)));
       // 准备参数
       DrugMarkingPageReqVO reqVO = new DrugMarkingPageReqVO();
       reqVO.setDataId(null);
       reqVO.setUserId(null);
       reqVO.setDrugId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DrugMarkingDO> pageResult = drugMarkingService.getDrugMarkingPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDrugMarking, pageResult.getList().get(0));
    }

}