package cn.iocoder.yudao.module.system.service.level;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.system.controller.admin.level.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import cn.iocoder.yudao.module.system.dal.mysql.level.LevelMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link LevelServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(LevelServiceImpl.class)
public class LevelServiceImplTest extends BaseDbUnitTest {

    @Resource
    private LevelServiceImpl levelService;

    @Resource
    private LevelMapper levelMapper;

    @Test
    public void testCreateLevel_success() {
        // 准备参数
        LevelSaveReqVO createReqVO = randomPojo(LevelSaveReqVO.class).setId(null);

        // 调用
        Long levelId = levelService.createLevel(createReqVO);
        // 断言
        assertNotNull(levelId);
        // 校验记录的属性是否正确
        LevelDO level = levelMapper.selectById(levelId);
        assertPojoEquals(createReqVO, level, "id");
    }

    @Test
    public void testUpdateLevel_success() {
        // mock 数据
        LevelDO dbLevel = randomPojo(LevelDO.class);
        levelMapper.insert(dbLevel);// @Sql: 先插入出一条存在的数据
        // 准备参数
        LevelSaveReqVO updateReqVO = randomPojo(LevelSaveReqVO.class, o -> {
            o.setId(dbLevel.getId()); // 设置更新的 ID
        });

        // 调用
        levelService.updateLevel(updateReqVO);
        // 校验是否更新正确
        LevelDO level = levelMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, level);
    }

    @Test
    public void testUpdateLevel_notExists() {
        // 准备参数
        LevelSaveReqVO updateReqVO = randomPojo(LevelSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> levelService.updateLevel(updateReqVO), LEVEL_NOT_EXISTS);
    }

    @Test
    public void testDeleteLevel_success() {
        // mock 数据
        LevelDO dbLevel = randomPojo(LevelDO.class);
        levelMapper.insert(dbLevel);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbLevel.getId();

        // 调用
        levelService.deleteLevel(id);
       // 校验数据不存在了
       assertNull(levelMapper.selectById(id));
    }

    @Test
    public void testDeleteLevel_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> levelService.deleteLevel(id), LEVEL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetLevelPage() {
       // mock 数据
       LevelDO dbLevel = randomPojo(LevelDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       levelMapper.insert(dbLevel);
       // 测试 name 不匹配
       levelMapper.insert(cloneIgnoreId(dbLevel, o -> o.setName(null)));
       // 测试 status 不匹配
       levelMapper.insert(cloneIgnoreId(dbLevel, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       levelMapper.insert(cloneIgnoreId(dbLevel, o -> o.setCreateTime(null)));
       // 准备参数
       LevelPageReqVO reqVO = new LevelPageReqVO();
       reqVO.setName(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<LevelDO> pageResult = levelService.getLevelPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbLevel, pageResult.getList().get(0));
    }

}