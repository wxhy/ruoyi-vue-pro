package cn.iocoder.yudao.module.lib.service.pharmacydrug;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.lib.controller.admin.pharmacydrug.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import cn.iocoder.yudao.module.lib.dal.mysql.pharmacydrug.PharmacyDrugMapper;
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
 * {@link PharmacyDrugServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(PharmacyDrugServiceImpl.class)
public class PharmacyDrugServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PharmacyDrugServiceImpl pharmacyDrugService;

    @Resource
    private PharmacyDrugMapper pharmacyDrugMapper;

    @Test
    public void testCreatePharmacyDrug_success() {
        // 准备参数
        PharmacyDrugSaveReqVO createReqVO = randomPojo(PharmacyDrugSaveReqVO.class).setId(null);

        // 调用
        Long pharmacyDrugId = pharmacyDrugService.createPharmacyDrug(createReqVO);
        // 断言
        assertNotNull(pharmacyDrugId);
        // 校验记录的属性是否正确
        PharmacyDrugDO pharmacyDrug = pharmacyDrugMapper.selectById(pharmacyDrugId);
        assertPojoEquals(createReqVO, pharmacyDrug, "id");
    }

    @Test
    public void testUpdatePharmacyDrug_success() {
        // mock 数据
        PharmacyDrugDO dbPharmacyDrug = randomPojo(PharmacyDrugDO.class);
        pharmacyDrugMapper.insert(dbPharmacyDrug);// @Sql: 先插入出一条存在的数据
        // 准备参数
        PharmacyDrugSaveReqVO updateReqVO = randomPojo(PharmacyDrugSaveReqVO.class, o -> {
            o.setId(dbPharmacyDrug.getId()); // 设置更新的 ID
        });

        // 调用
        pharmacyDrugService.updatePharmacyDrug(updateReqVO);
        // 校验是否更新正确
        PharmacyDrugDO pharmacyDrug = pharmacyDrugMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, pharmacyDrug);
    }

    @Test
    public void testUpdatePharmacyDrug_notExists() {
        // 准备参数
        PharmacyDrugSaveReqVO updateReqVO = randomPojo(PharmacyDrugSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> pharmacyDrugService.updatePharmacyDrug(updateReqVO), PHARMACY_DRUG_NOT_EXISTS);
    }

    @Test
    public void testDeletePharmacyDrug_success() {
        // mock 数据
        PharmacyDrugDO dbPharmacyDrug = randomPojo(PharmacyDrugDO.class);
        pharmacyDrugMapper.insert(dbPharmacyDrug);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPharmacyDrug.getId();

        // 调用
        pharmacyDrugService.deletePharmacyDrug(id);
       // 校验数据不存在了
       assertNull(pharmacyDrugMapper.selectById(id));
    }

    @Test
    public void testDeletePharmacyDrug_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> pharmacyDrugService.deletePharmacyDrug(id), PHARMACY_DRUG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPharmacyDrugPage() {
       // mock 数据
       PharmacyDrugDO dbPharmacyDrug = randomPojo(PharmacyDrugDO.class, o -> { // 等会查询到
           o.setApprovalNumber(null);
           o.setCommonName(null);
           o.setManufacturer(null);
           o.setProductBatch(null);
           o.setUserId(null);
           o.setWatch(null);
           o.setCreateTime(null);
       });
       pharmacyDrugMapper.insert(dbPharmacyDrug);
       // 测试 approvalNumber 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setApprovalNumber(null)));
       // 测试 commonName 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setCommonName(null)));
       // 测试 manufacturer 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setManufacturer(null)));
       // 测试 productBatch 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setProductBatch(null)));
       // 测试 userId 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setUserId(null)));
       // 测试 watch 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setWatch(null)));
       // 测试 createTime 不匹配
       pharmacyDrugMapper.insert(cloneIgnoreId(dbPharmacyDrug, o -> o.setCreateTime(null)));
       // 准备参数
       PharmacyDrugPageReqVO reqVO = new PharmacyDrugPageReqVO();
       reqVO.setApprovalNumber(null);
       reqVO.setCommonName(null);
       reqVO.setManufacturer(null);
       reqVO.setProductBatch(null);
       reqVO.setUserId(null);
       reqVO.setWatch(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<PharmacyDrugDO> pageResult = pharmacyDrugService.getPharmacyDrugPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPharmacyDrug, pageResult.getList().get(0));
    }

}