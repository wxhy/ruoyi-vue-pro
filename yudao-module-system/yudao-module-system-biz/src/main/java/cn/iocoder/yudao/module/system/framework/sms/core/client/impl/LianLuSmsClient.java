package cn.iocoder.yudao.module.system.framework.sms.core.client.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.framework.common.util.collection.ArrayUtils;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.iocoder.yudao.module.system.framework.sms.core.enums.SmsTemplateAuditStatusEnum;
import cn.iocoder.yudao.module.system.framework.sms.core.property.SmsChannelProperties;
import cn.iocoder.yudao.module.system.framework.sms.lianlu.common.Credential;
import cn.iocoder.yudao.module.system.framework.sms.lianlu.models.Product;
import cn.iocoder.yudao.module.system.framework.sms.lianlu.models.SmsSend;
import com.alibaba.fastjson.JSONObject;
import com.google.common.annotations.VisibleForTesting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author qiuhongyun
 * @date 2024-05-31 14:36
 */
public class LianLuSmsClient extends AbstractSmsClient{

    /**
     * 调用成功 code
     */
    public static final String API_CODE_SUCCESS = "00";

    public LianLuSmsClient(SmsChannelProperties properties) {
        super(properties);
        Assert.notEmpty(properties.getApiKey(), "apiKey 不能为空");
        Assert.notEmpty(properties.getApiSecret(), "apiSecret 不能为空");
    }

    /**
     * 发送消息
     *
     * @param logId          日志编号
     * @param mobile         手机号
     * @param apiTemplateId  短信 API 的模板编号
     * @param templateParams 短信模板参数。通过 List 数组，保证参数的顺序
     * @return 短信发送结果
     */
    @Override
    public SmsSendRespDTO sendSms(Long logId, String mobile, String apiTemplateId, List<KeyValue<String, Object>> templateParams) throws Throwable {
        Credential credential = new Credential("1009492", properties.getApiKey(), properties.getApiSecret(), true);

        SmsSend smsSend = new SmsSend();
        smsSend.setPhoneNumberSet(new String[] {mobile});
        smsSend.setTemplateId(apiTemplateId);
        smsSend.setSignName(properties.getSignature());
        if (CollUtil.isNotEmpty(templateParams)) {
            smsSend.setTemplateParamSet(ArrayUtils.toArray(templateParams, e -> String.valueOf(e.getValue())));
        }
        JSONObject jsonObject = smsSend.TemplateSend(credential, smsSend);

        return new SmsSendRespDTO().setSuccess(Objects.equals(jsonObject.getString("status"), API_CODE_SUCCESS)).setSerialNo(jsonObject.getString("taskId"))
                .setApiRequestId(jsonObject.getString("taskId")).setApiCode(jsonObject.getString("status")).setApiMsg(jsonObject.getString("message"));
    }

    /**
     * 解析接收短信的接收结果
     *
     * @param text 结果
     * @return 结果内容
     * @throws Throwable 当解析 text 发生异常时，则会抛出异常
     */
    @Override
    public List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) throws Throwable {
        return Collections.emptyList();
    }

    /**
     * 查询指定的短信模板
     *
     * @param apiTemplateId 短信 API 的模板编号
     * @return 短信模板
     */
    @Override
    public SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) throws Throwable {
        Credential credential = new Credential("1009492", properties.getApiKey(), properties.getApiSecret(), true);
        Product product = new Product();
        JSONObject jsonObject = product.GetTemplate(credential, Integer.valueOf(apiTemplateId));

        return new SmsTemplateRespDTO().setId(jsonObject.getString("id")).setContent(jsonObject.getString("content"))
                .setAuditStatus(convertSmsTemplateAuditStatus(jsonObject.getInteger("status"))).setAuditReason(jsonObject.getString("refuseReason"));
    }

    @VisibleForTesting
    Integer convertSmsTemplateAuditStatus(int templateStatus) {
        switch (templateStatus) {
            case 1: return SmsTemplateAuditStatusEnum.SUCCESS.getStatus();
            case 2: return SmsTemplateAuditStatusEnum.CHECKING.getStatus();
            case 3: return SmsTemplateAuditStatusEnum.FAIL.getStatus();
            default: throw new IllegalArgumentException(String.format("未知审核状态(%d)", templateStatus));
        }
    }


    /**
     * 自定义初始化
     */
    @Override
    protected void doInit() {

    }

    private String getSdkAppId() {
        return StrUtil.subAfter(properties.getApiSecret(), " ", true);
    }

    private String getApiKey() {
        return StrUtil.subBefore(properties.getApiKey(), " ", true);
    }

}
