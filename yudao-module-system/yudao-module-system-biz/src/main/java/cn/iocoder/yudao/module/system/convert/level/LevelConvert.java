package cn.iocoder.yudao.module.system.convert.level;

import cn.iocoder.yudao.module.system.api.level.dto.LevelDTO;
import cn.iocoder.yudao.module.system.controller.admin.level.vo.LevelRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.level.LevelDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LevelConvert {
    LevelConvert INSTANCE = Mappers.getMapper(LevelConvert.class);

    LevelRespVO convert(LevelDO bean);

    List<LevelRespVO> convertList(List<LevelDO> list);

    LevelDTO convert02(LevelDO level);
}
