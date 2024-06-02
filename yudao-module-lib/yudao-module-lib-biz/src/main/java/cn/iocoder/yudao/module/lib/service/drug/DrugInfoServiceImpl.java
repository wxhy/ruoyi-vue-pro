package cn.iocoder.yudao.module.lib.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.lib.controller.admin.drug.vo.DrugInfoPageReqVO;
import cn.iocoder.yudao.module.lib.controller.admin.drug.vo.DrugInfoSaveReqVO;
import cn.iocoder.yudao.module.lib.dal.dataobject.drug.DrugInfoDO;
import cn.iocoder.yudao.module.lib.dal.mysql.drug.DrugInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 药品爬虫 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DrugInfoServiceImpl extends ServiceImpl<DrugInfoMapper, DrugInfoDO> implements DrugInfoService {

    @Resource
    private DrugInfoMapper drugInfoMapper;

    @Override
    public Integer createDrugInfo(DrugInfoSaveReqVO createReqVO) {
        // 插入
        DrugInfoDO drugInfo = BeanUtils.toBean(createReqVO, DrugInfoDO.class);
        drugInfoMapper.insert(drugInfo);
        // 返回
        return drugInfo.getId();
    }

    @Override
    public void updateDrugInfo(DrugInfoSaveReqVO updateReqVO) {
        // 更新
        DrugInfoDO updateObj = BeanUtils.toBean(updateReqVO, DrugInfoDO.class);
        drugInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteDrugInfo(Integer id) {
        // 删除
        drugInfoMapper.deleteById(id);
    }


    @Override
    public DrugInfoDO getDrugInfo(Integer id) {
        return drugInfoMapper.selectById(id);
    }

    @Override
    public PageResult<DrugInfoDO> getDrugInfoPage(DrugInfoPageReqVO pageReqVO) {
        return drugInfoMapper.selectPage(pageReqVO);
    }

}