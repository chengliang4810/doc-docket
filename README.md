# 接口文档配置文件说明:
本项目的作用是为了省去手动构建和注入DocDocket对象而创建的。


## 通用配置示例如下
```yml
# 通用属性配置案例， common的配置可以被下方的具体配置覆盖
# 注意: 请勿将groupName与packageName配置到common,因为就算你配置了也不会加载 🤣
solon.docs.common:
  # 默认是否启用
  enabled: true
  # 全局响应体是否在数据中 默认为 true
  globalResponseInData: true
  # 全局响应体类型 默认为 org.noear.solon.core.handle.Result
  globalResult: org.noear.solon.core.handle.Result
  # 标题
  title: '管理系统_接口文档'
  # 描述
  description: '描述：用于管理公司的人员信息,具体包括XXX,XXX模块...'
  # 版本
  version: '版本号: 1.0.0'
  # 作者信息
  contact:
    name: chengliang4810
    email: chengliang4810@163.com
    url: https://gitee.com/chengliang4810

solon:
  docs:
    - # 分组名 默认为 default
      groupName: 1.测试模块
      # 包路径 必填
      packageName: com.layjava.test
    # 是否开启接口文档 默认为 true
    - groupName: 2.通用模块
      # 包路径
      packageName: com.layjava.web
```

## 单分组配置如下
```yaml
solon:
  docs:
    # 是否开启接口文档 默认为 true
    enabled: true
    # 分组名 默认为 default
    groupName: 1.测试模块
    # 包路径 必填
    packageName: com.layjava.test
    # 全局响应体是否在数据中 默认为 true
    globalResponseInData: true
    # 全局响应体类型 默认为 org.noear.solon.core.handle.Result
    globalResult: org.noear.solon.core.handle.Result
    # 标题
    title: '管理系统_接口文档'
    # 描述
    description: '描述：用于管理公司的人员信息,具体包括XXX,XXX模块...'
    # 版本
    version: '版本号: 1.0.0'
    # 作者信息
    contact:
      name: chengliang4810
      email: chengliang4810@163.com
      url: https://gitee.com/chengliang4810
```

## 多分组配置如下
```yaml
solon:
  docs:
    # 是否开启接口文档 默认为 true
    - enabled: true
      # 分组名 默认为 default
      groupName: 1.测试模块
      # 包路径 必填
      packageName: com.layjava.test
      # 全局响应体是否在数据中 默认为 true
      globalResponseInData: true
      # 全局响应体类型 默认为 org.noear.solon.core.handle.Result
      globalResult: org.noear.solon.core.handle.Result
      # 标题
      title: '管理系统_接口文档'
      # 描述
      description: '描述：用于管理公司的人员信息,具体包括XXX,XXX模块...'
      # 版本
      version: '版本号: 1.0.0'
      # 作者信息
      contact:
        name: chengliang4810
        email: chengliang4810@163.com
        url: https://gitee.com/chengliang4810

    # 是否开启接口文档 默认为 true
    - enabled: true
      # 分组名
      groupName: 2.通用模块
      # 包路径
      packageName: com.layjava.web
      # 标题
      title: '管理系统_接口文档'
      # 描述
      description: '描述：用于管理公司的人员信息,具体包括XXX,XXX模块...'
      # 版本
      version: '版本号: 1.0.0'
      # 作者信息
      contact:
        name: chengliang4810
        email: chengliang4810@163.com
        url: https://gitee.com/chengliang4810
```

# 使用方式
1. 引入solon-docs-openapi2-javadoc依赖
2. 按照上方配置文件进行配置
3. 配置Maven插件, 不配置则无法获取到注释信息(可以先尝试不配置)
```xml
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>${maven-compiler.version}</version>
                    <configuration>
                        <compilerArgument>-parameters</compilerArgument>
                        <source>${maven.compiler.source}</source>
                        <target>${maven.compiler.target}</target>
                        <encoding>UTF-8</encoding>
                        <annotationProcessorPaths>
                            <path>
                                <groupId>com.github.therapi</groupId>
                                <artifactId>therapi-runtime-javadoc-scribe</artifactId>
                                <version>${therapi-runtime-javadoc.version}</version>
                            </path>
                        </annotationProcessorPaths>
                        <compilerArgs>
                            <arg>
                                -Amapstruct.defaultComponentModel=solon
                            </arg>
                        </compilerArgs>
                    </configuration>
                </plugin>
```
> 重点是 therapi-runtime-javadoc-scribe 部分,其他参数看你心情

4. 访问文档接口 http://localhost:8080/swagger/v2?group=1.%E6%B5%8B%E8%AF%95%E6%A8%A1%E5%9D%97
5. 没有 UI 界面，使用Apifox 等工具进行导入，按照下图方式导入。导入完成后再接口管理中查看进行管理。