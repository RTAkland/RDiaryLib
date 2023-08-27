# RDBLib

<div align="center">
<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/RTAkland/RDBLib/ci.yaml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.8.22-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/RTAkland/QWeatherSDK?logo=apache">
<a href="https://jitpack.io/#RTAkland/RDBLib"><img alt="jitpackV" src="https://jitpack.io/v/RTAkland/RDBLib.svg"></a>

</div>

# 简介
> 这是一个日记本API库的项目， 通过本API来与GitHub存储库交互， 
> 提交文件后仓库会通知Serverless服务器构建日记的静态页面， 
> 此库正在开发中暂时无法 使用， 另外还有配套的multiplatform的应用，
> 首先开发的是桌面端使用Compose for Desktop


> 纯Kotlin开发的API库可以百分百之支持Java调用以此实现跨平台

# 使用
* 可以使用`jitpack`来快速获取此库,步骤如下:

## gradle
```gradle
  // 添加maven仓库
repositories {
    // ...其他仓库
    maven { url 'https://www.jitpack.io' }
}
```

```gradle
  // 添加依赖 
dependencies {
  implementation 'com.github.RTAkland:RDBLib:1.0-snapshot'  // 请替换到最新版本
}
```

## maven 

```xml
  //  添加jitpack maven存储库
<repositories>
  // ...其他仓库
  <repository>
    <id>jitpack.io</id>
    <url>https://www.jitpack.io</url>
  </repository>
</repositories>
```

```xml
  // 添加依赖
<dependency>
  <groupId>com.github.RTAkland</groupId>
  <artifactId>RDBLib</artifactId>
  <version>1.0-snapshot</version>
</dependency>
```

> API库版本查询地址 `https://www.jitpack.io/#RTAkland/RDBLib`

### 使用far-aar
> `fat-aar`是一种打包发方式， 可以将所有依赖的源代码都打包进最终的jar文件中
> 以下为`gradle`的配置方法

```gradle
// build.gradle

// 添加embed函数
configurations {
    embed
    compile.extendsFrom(embed)
}

// ...

// 添加jar任务
jar {
    from configurations.embed.collect {
        it.isDirectory() ? it : zipTree(it)
    }
}

// ...

dependencies {
    // 示例： 使用embed函数包裹api implementation等函数即可， 但是
    // 需要在上方再添加一个相同的 api implementation声明
    
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    embed(api("com.squareup.okhttp3:okhttp:5.0.0-alpha.11"))
    // or embed(implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11"))
}
```

# 开源

- 本项目以[Apache-2.0](./LICENSE)许可开源, 即:
    - 你可以直接使用该项目提供的功能, 无需任何授权
    - 你可以在**注明来源版权信息**的情况下对源代码进行任意分发和修改以及衍生
 
# 鸣谢

<div>

<img src="https://static.rtast.cn/static/other/jetbrains.png" alt="JetBrainsIcon" width="128">

<a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> 提供的强大IDE支持

</div>
