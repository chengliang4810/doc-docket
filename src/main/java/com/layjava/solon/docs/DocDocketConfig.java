package com.layjava.solon.docs;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.layjava.solon.docs.properties.DocketProperty;
import com.layjava.solon.docs.properties.DocsProperty;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.docs.DocDocket;
import org.noear.solon.docs.models.ApiContact;
import org.noear.solon.docs.models.ApiInfo;

@Configuration
@Import(profilesIfAbsent = {"classpath: docs.yml"})
public class DocDocketConfig {

    /**
     * 前缀名称
     */
    private static final String PREFIX_NAME = "com.layjava.docs.javadoc.solon.plugin-";
    /**
     * 忽略属性
     */
    private static final String[] IGNORE_PROPERTIES = new String[]{"groupName", "packageName"};
    /**
     * 批量分组配置
     */
    @Inject(value = "${solon}", required = false)
    private DocketProperty docketProperty;
    /**
     * 单分组配置
     */
    @Inject(value = "${solon.docs}", required = false)
    private DocsProperty docsProperty;

    /**
     * 构建 DocDocket 实例，并配置相关信息
     */
    @Bean
    public void buildDocDocket() {

        // 未配置多分组，进行初始化
        if (docketProperty == null){
            docketProperty = new DocketProperty();
        }

        // 将文档配置添加到多分组配置中
        if (docsProperty != null) {
            docketProperty.getDocs().add(docsProperty);
        }

        // 检查参数
        if (CollUtil.isEmpty(docketProperty.getDocs())) {
            return;
        }

        docketProperty.getDocs().stream()
                // 获取所有启用
                .filter(docsProperty -> Boolean.TRUE.equals(docsProperty.isEnabled()))
                .forEach(docsProperty -> {
                    //复制属性
                    if (this.docsProperty != null && this.docsProperty.getCommon() != null){
                        BeanUtil.copyProperties(this.docsProperty.getCommon(), docsProperty, CopyOptions.create().setOverride(false).setIgnoreProperties(IGNORE_PROPERTIES));
                    }
                    //构建 DocDocket 实例
                    DocDocket docDocket = new DocDocket();
                    docDocket.groupName(docsProperty.getGroupName());

                    //配置API信息
                    ApiInfo apiInfo = new ApiInfo().title(docsProperty.getTitle())
                            .description(docsProperty.getDescription())
                            .version(docsProperty.getVersion());

                    //配置联系人信息
                    if (docsProperty.getContact() != null) {
                        apiInfo.contact(new ApiContact()
                                .name(docsProperty.getContact().getName())
                                .url(docsProperty.getContact().getUrl())
                                .email(docsProperty.getContact().getEmail())
                        );
                    }

                    docDocket.info(apiInfo)
                            // 配置扫描的包路径
                            .apis(docsProperty.getPackageName())
                            .globalResult(docsProperty.getGlobalData())
                            .globalResponseInData(docsProperty.getGlobalResponseInData())
                            .schemes(docsProperty.getSchemes().toArray(new String[0]));

                    //包装Bean（指定名字）
                    BeanWrap beanWrap = Solon.context().wrap(DocDocket.class, docDocket);
                    Solon.context().putWrap(PREFIX_NAME + docsProperty.getGroupName(), beanWrap);
                });

    }


}
