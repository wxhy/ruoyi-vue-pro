package cn.iocoder.yudao.module.lib.service.drugyf;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.module.lib.dal.mysql.drugyf.DrugYfMapper;
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
 * {@link DrugYfServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(DrugYfServiceImpl.class)
public class DrugYfServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DrugYfServiceImpl drugYfService;

    @Resource
    private DrugYfMapper drugYfMapper;

    @Test
    public void testCreateDrugYf_success() {
        // 准备参数
        DrugYfSaveReqVO createReqVO = randomPojo(DrugYfSaveReqVO.class).setId(null);

        // 调用
        Long drugYfId = drugYfService.createDrugYf(createReqVO);
        // 断言
        assertNotNull(drugYfId);
        // 校验记录的属性是否正确
        DrugYfDO drugYf = drugYfMapper.selectById(drugYfId);
        assertPojoEquals(createReqVO, drugYf, "id");
    }

    @Test
    public void testUpdateDrugYf_success() {
        // mock 数据
        DrugYfDO dbDrugYf = randomPojo(DrugYfDO.class);
        drugYfMapper.insert(dbDrugYf);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DrugYfSaveReqVO updateReqVO = randomPojo(DrugYfSaveReqVO.class, o -> {
            o.setId(dbDrugYf.getId()); // 设置更新的 ID
        });

        // 调用
        drugYfService.updateDrugYf(updateReqVO);
        // 校验是否更新正确
        DrugYfDO drugYf = drugYfMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, drugYf);
    }

    @Test
    public void testUpdateDrugYf_notExists() {
        // 准备参数
        DrugYfSaveReqVO updateReqVO = randomPojo(DrugYfSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> drugYfService.updateDrugYf(updateReqVO), DRUG_YF_NOT_EXISTS);
    }

    @Test
    public void testDeleteDrugYf_success() {
        // mock 数据
        DrugYfDO dbDrugYf = randomPojo(DrugYfDO.class);
        drugYfMapper.insert(dbDrugYf);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDrugYf.getId();

        // 调用
        drugYfService.deleteDrugYf(id);
       // 校验数据不存在了
       assertNull(drugYfMapper.selectById(id));
    }

    @Test
    public void testDeleteDrugYf_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> drugYfService.deleteDrugYf(id), DRUG_YF_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDrugYfPage() {
       // mock 数据
       DrugYfDO dbDrugYf = randomPojo(DrugYfDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setApprovalNumber(null);
           o.setPacking(null);
           o.setDosageForm(null);
           o.setProductionEnterPrise(null);
           o.setCreateTime(null);
       });
       drugYfMapper.insert(dbDrugYf);
       // 测试 name 不匹配
       drugYfMapper.insert(cloneIgnoreId(dbDrugYf, o -> o.setName(null)));
       // 测试 approvalNumber 不匹配
       drugYfMapper.insert(cloneIgnoreId(dbDrugYf, o -> o.setApprovalNumber(null)));
       // 测试 packing 不匹配
       drugYfMapper.insert(cloneIgnoreId(dbDrugYf, o -> o.setPacking(null)));
       // 测试 dosageForm 不匹配
       drugYfMapper.insert(cloneIgnoreId(dbDrugYf, o -> o.setDosageForm(null)));
       // 测试 productionEnterPrise 不匹配
       drugYfMapper.insert(cloneIgnoreId(dbDrugYf, o -> o.setProductionEnterPrise(null)));
       // 测试 createTime 不匹配
       drugYfMapper.insert(cloneIgnoreId(dbDrugYf, o -> o.setCreateTime(null)));
       // 准备参数
       DrugYfPageReqVO reqVO = new DrugYfPageReqVO();
       reqVO.setName(null);
       reqVO.setApprovalNumber(null);
       reqVO.setPacking(null);
       reqVO.setDosageForm(null);
       reqVO.setProductionEnterPrise(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DrugYfDO> pageResult = drugYfService.getDrugYfPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDrugYf, pageResult.getList().get(0));
    }

}