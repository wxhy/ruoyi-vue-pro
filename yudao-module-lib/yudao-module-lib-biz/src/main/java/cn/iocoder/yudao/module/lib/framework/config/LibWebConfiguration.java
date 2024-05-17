package cn.iocoder.yudao.module.lib.framework.config;

import cn.iocoder.yudao.framework.swagger.config.YudaoSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * lib 模块的 web 组件的 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class LibWebConfiguration {

    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi libGroupedOpenApi() {
        return YudaoSwaggerAutoConfiguration.buildGroupedOpenApi("lib");
    }

}
