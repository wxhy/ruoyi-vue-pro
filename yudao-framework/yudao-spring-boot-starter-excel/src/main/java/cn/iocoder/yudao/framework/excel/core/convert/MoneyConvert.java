package cn.iocoder.yudao.framework.excel.core.convert;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额转换器
 * <p>
 * 金额单位：分
 *
 * @author 芋道源码
 */
public class MoneyConvert implements Converter<BigDecimal> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        BigDecimal result = value
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        return new WriteCellData<>(result.toString());
    }


    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                        GlobalConfiguration globalConfiguration) throws Exception {
        switch (cellData.getType()) {
            case STRING:
                return new BigDecimal(cellData.getStringValue());
            case NUMBER:
                return cellData.getNumberValue();
            default:
                throw new IllegalArgumentException("Unknown data type: " + cellData.getType());
        }
    }
}
