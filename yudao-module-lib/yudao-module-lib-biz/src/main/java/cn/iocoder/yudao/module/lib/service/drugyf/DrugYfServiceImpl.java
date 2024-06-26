package cn.iocoder.yudao.module.lib.service.drugyf;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lib.controller.admin.drugyf.vo.*;
import cn.iocoder.yudao.module.lib.dal.dataobject.drugyf.DrugYfDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lib.dal.mysql.drugyf.DrugYfMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lib.enums.ErrorCodeConstants.*;

/**
 * 药房药品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DrugYfServiceImpl extends ServiceImpl<DrugYfMapper, DrugYfDO> implements DrugYfService {

    @Resource
    private DrugYfMapper drugYfMapper;

    @Override
    public Long createDrugYf(DrugYfSaveReqVO createReqVO) {
        // 插入
        DrugYfDO drugYf = BeanUtils.toBean(createReqVO, DrugYfDO.class);
        drugYfMapper.insert(drugYf);
        // 返回
        return drugYf.getId();
    }

    @Override
    public void updateDrugYf(DrugYfSaveReqVO updateReqVO) {
        // 校验存在
        validateDrugYfExists(updateReqVO.getId());
        // 更新
        DrugYfDO updateObj = BeanUtils.toBean(updateReqVO, DrugYfDO.class);
        drugYfMapper.updateById(updateObj);
    }

    @Override
    public void deleteDrugYf(Long id) {
        // 校验存在
        validateDrugYfExists(id);
        // 删除
        drugYfMapper.deleteById(id);
    }

    private void validateDrugYfExists(Long id) {
        if (drugYfMapper.selectById(id) == null) {
            throw exception(DRUG_YF_NOT_EXISTS);
        }
    }

    @Override
    public DrugYfDO getDrugYf(Long id) {
        return drugYfMapper.selectById(id);
    }

    @Override
    public PageResult<DrugYfDO> getDrugYfPage(DrugYfPageReqVO pageReqVO) {
        return drugYfMapper.selectPage(pageReqVO);
    }

    /**
     * 获得药房药品列表
     *
     * @param drugName
     * @return 药房药品列表
     */
    @Override
    public List<DrugYfDO> getDrugYfList(String drugName) {
        return drugYfMapper.selectList(new LambdaQueryWrapperX<DrugYfDO>()
                .eq(DrugYfDO::getName, drugName));
    }

    /**
     * 获取样品列表
     *
     * @param ids
     * @return
     */
    @Override
    public List<DrugYfDO> getDrugYfList(Collection<Long> ids) {
        return drugYfMapper.selectBatchIds(ids);
    }

    @Override
    public List<DrugYfDO> getDrugYfList(Collection<Long> ids, String updateTime) {
        return drugYfMapper.selectList(new LambdaQueryWrapperX<DrugYfDO>()
                .in(DrugYfDO::getId, ids)
                .ge(DrugYfDO::getUpdateTime, updateTime));
    }

    @Override
    public DrugYfDO getDrugYfByUrl(String url) {
        return drugYfMapper.selectOne(DrugYfDO::getUrl, url);
    }

    /**
     * 获取药品信息
     *
     * @param dataId
     * @return
     */
    @Override
    public DrugYfDO getDrugYfByDataId(Long dataId) {
        return drugYfMapper.selectOne(DrugYfDO::getDataId, dataId);
    }
}