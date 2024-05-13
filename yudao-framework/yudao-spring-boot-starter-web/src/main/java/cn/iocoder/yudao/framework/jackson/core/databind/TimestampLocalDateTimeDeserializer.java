package cn.iocoder.yudao.framework.jackson.core.databind;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 基于时间戳的 LocalDateTime 反序列化器
 *
 * @author 老五
 */
public class TimestampLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final TimestampLocalDateTimeDeserializer INSTANCE = new TimestampLocalDateTimeDeserializer();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final long timestamp = p.getValueAsLong();
        if (timestamp > 0) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        } else {
            LocalDateTime time = null;
            final String valueAsString = p.getValueAsString();
            // 判空
            if (!StringUtils.isBlank(valueAsString)) {
                // 将String格式转成Date
                final Date dateTime = DateUtil.parseDateTime(valueAsString);
                // 将Date转成LocalDateTime
                time = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            return time;
        }
    }

}
