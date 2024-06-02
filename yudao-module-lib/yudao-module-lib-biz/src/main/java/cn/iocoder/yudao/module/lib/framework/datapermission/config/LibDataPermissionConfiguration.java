package cn.iocoder.yudao.module.lib.framework.datapermission.config;

import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.iocoder.yudao.module.lib.dal.dataobject.pharmacydrug.PharmacyDrugDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class LibDataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer libPermissionRuleCustomizer() {
        return rule -> {
            // user
            rule.addUserColumn(PharmacyDrugDO.class, "user_id");
        };
    }

}
