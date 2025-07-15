# Todo-Java-CEF 脚手架

<div align="center">
  <h3>Java + Chromium = 现代桌面应用</h3>
  <p>一个功能完善的Java Chromium Embedded Framework(JCEF)桌面应用脚手架，提供嵌入式Chromium浏览器与Java Swing的无缝集成。</p>
  
  ![Java Version](https://img.shields.io/badge/Java-17%2B-blue)
  ![License](https://img.shields.io/badge/License-MIT-green)
  ![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey)
</div>

## 项目简介

Todo-Java-CEF是一个开源的桌面应用脚手架，使用JCEF将Chromium浏览器嵌入到Java Swing窗口中，实现Web内容的展示和交互。该脚手架提供了一套完整的基础设施，包括窗口管理、系统托盘、全局快捷键、JavaScript交互等功能，让开发者可以快速构建基于Web技术的桌面应用。

### 为什么选择Todo-Java-CEF？

- **现代Web技术** - 使用HTML5、CSS3和JavaScript构建UI，同时保留Java的强大后端能力
- **跨平台兼容** - 支持Windows、macOS和Linux
- **开箱即用** - 预配置了常用功能，无需从零开始
- **高性能** - 使用Chromium渲染引擎，确保Web内容的高性能展示
- **易于扩展** - 模块化设计，便于添加自定义功能

## 技术栈

- **Java 17+** - 利用现代Java特性
- **Swing** - 经典Java GUI框架
- **JCEF** - Java Chromium Embedded Framework
- **Maven** - 项目构建和依赖管理
- **Gson** - JSON处理
- **SLF4J/Logback** - 日志框架

## 核心功能

### 基础功能
- **嵌入式Chromium浏览器** - 在Java Swing窗口中无缝集成现代Web浏览器
- **本地CEF加载** - 使用本地安装的CEF，避免网络下载问题
- **环境适配** - 自动识别开发/生产环境，分别加载开发服务器或本地静态文件

### 系统集成
- **系统托盘集成**
  - 自定义托盘图标和菜单
  - 托盘通知功能
  - 双击托盘图标显示/隐藏主窗口
- **窗口管理**
  - 窗口最小化到托盘
  - 窗口显示/隐藏控制
  - 窗口关闭事件自定义处理
- **全局快捷键** - 通过快捷键组合控制应用窗口

### 交互功能
- **JavaScript交互**
  - 从Java执行JavaScript代码
  - 通过消息路由器实现JavaScript与Java的双向通信
- **事件处理**
  - 键盘焦点事件监听
  - 鼠标点击事件处理
  - 窗口状态变化监听

### 高级特性
- **CEF高级配置**
  - 透明背景支持
  - GPU渲染控制
  - 沙箱配置
- **资源管理** - 应用关闭时自动释放CEF资源
- **日志系统** - 集成SLF4J日志框架，提供详细运行日志

## 安装与使用

### 系统要求

- JDK 17或更高版本
- 支持的操作系统：Windows、macOS、Linux
- 至少4GB RAM（推荐8GB或更多）
- 至少200MB可用磁盘空间（不包括CEF二进制文件）

### 获取CEF二进制文件

CEF二进制文件是运行应用的必要组件，但由于文件较大，不包含在代码仓库中。您需要手动下载并放置在正确位置：

1. 访问 [JCEF Maven Releases](https://github.com/jcefmaven/jcefmaven/releases) 下载与您操作系统匹配的CEF二进制文件
2. 解压下载的文件
3. 将解压后的文件夹重命名为`runtimes`并放置在项目根目录下

> **注意**：确保下载的CEF版本与项目中使用的JCEF Maven版本兼容

### 快速开始

1. 克隆仓库
   ```bash
   git clone https://github.com/yourusername/todo-java-cef.git
   cd todo-java-cef
   ```

2. 确保CEF二进制文件已放置在`runtimes`目录下

3. 使用Maven构建和运行
   ```bash
   mvn clean compile exec:java
   ```

### 开发模式

脚手架支持两种运行模式：

- **开发模式**：加载`http://localhost:3000/index.html`，适合前端开发调试
- **生产模式**：加载本地静态文件`/static/index.html`，适合打包发布

通过环境变量 `app.env`的值 `dev` `prod` 在两种模式间切换：


## 项目结构

```
todo-java-cef/
├── runtimes/          # CEF二进制文件目录
│   └── windows-amd64/
│       ├── swiftshader/
│       ├── jcef.dll
│       ├── ...
│   └── macos-amd64/
│       ├── swiftshader/
│       ├── jcef.dll
│       ├── ...
│   └── linux-amd64/
│       ├── swiftshader/
│       ├── jcef.dll/
│       ├── ...
│   └── ...
├── static/            # 生产环境静态资源目录
│   └── index.html     # 主页面
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── demo/
│       │           ├── Main.java                # 主入口类
│       │           └── common/                  # 通用工具类
│       │               ├── AppConstant.java     # 应用常量,通用方法
│       │               ├── AppEnum.java         # 环境变量
│       │               ├── FrameVisibleManager.java  # 窗体快捷键
│       │               ├── KeyboardHandler.java      # 键盘事件处理
│       │               ├── LogUtil.java              # 日志工具
│       │               ├── MyCefLoadHandler.java     # CEF加载处理
│       │               ├── MyCefMessageRouterHandler.java  # 消息路由处理
│       │               └── TrayDialog.java           # 托盘对话框
│       └── resources/
│           └── logback.xml  # 日志配置
└── pom.xml            # Maven构建配置文件
```

## 自定义开发指南

### 修改应用名称和窗口大小

在`Main.java`中修改相关常量：

```java
// 修改应用名称
private static final String APP_NAME = "你的应用名称";

// 修改窗口大小
mainFrame.setSize(1024, 768); // 宽度, 高度
```

### 修改加载的URL

在`Main.java`中修改`pageUrl`变量：

```java
// 开发环境
pageUrl = "http://localhost:你的端口/index.html";

// 生产环境
pageUrl = "file:///" + staticDir.replace('\\', '/') + "/你的页面.html";
```

### JavaScript与Java交互

#### 从Java调用JavaScript

```java
// 在Java代码中执行JavaScript
browser.executeJavaScript(
    "document.getElementById('myElement').innerHTML = '来自Java的数据';", 
    browser.getURL(), 
    0
);
```

#### 从JavaScript调用Java

1. 在`MyCefMessageRouterHandler.java`中添加处理方法：

```java
@Override
public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, 
                      String request, boolean persistent, CefQueryCallback callback) {
    // 解析来自JavaScript的请求
    try {
        JSONObject jsonRequest = new JSONObject(request);
        String action = jsonRequest.getString("action");
        
        if ("greeting".equals(action)) {
            String name = jsonRequest.getString("name");
            String response = "你好, " + name + "!";
            
            // 发送响应回JavaScript
            callback.success(response);
            return true;
        }
    } catch (Exception e) {
        logger.error("处理JavaScript查询时出错", e);
        callback.failure(0, e.getMessage());
    }
    return false;
}
```

2. 在JavaScript中调用Java方法：

```javascript
// 从JavaScript调用Java
window.cefQuery({
    request: JSON.stringify({
        action: "greeting",
        name: "世界"
    }),
    onSuccess: function(response) {
        console.log("来自Java的响应:", response);
        alert(response);
    },
    onFailure: function(error_code, error_message) {
        console.error("调用Java失败:", error_message);
    }
});
```

### 自定义托盘菜单

在`Main.java`中修改托盘菜单：

```java
// 添加自定义菜单项
popupMenu.add(AppConstant.createJMenuItem("设置", e -> {
        logger.info("点击自定义菜单");
}));
```

### 全局快捷键配置

修改`FrameVisibleManager`类中的快捷键组合：

```java
// 示例：修改为Alt+M
private static final int HOTKEY_MODIFIER = NativeKeyEvent.VC_ALT;
private static final int HOTKEY_KEYCODE = NativeKeyEvent.VC_M;
```

## 打包与发布

### 使用Maven打包

创建可执行JAR文件：

```bash
mvn clean package
```

这将在`target`目录下生成一个可执行的JAR文件。并在build目录下生成所有需要的文件.
在build目录下使用java -jar todo-java-cef-1.0-SNAPSHOT.jar 执行构建后程序 

### 创建本机安装程序 (还未测试)

推荐使用[jpackage](https://docs.oracle.com/en/java/javase/17/docs/specs/man/jpackage.html)（JDK 16+内置）创建本机安装程序：

```bash
jpackage --input target/ \
  --name "Todo App" \
  --main-jar todo-java-cef-1.0-SNAPSHOT.jar \
  --main-class org.demo.Main \
  --type dmg \  # 根据平台选择：dmg, pkg, exe, msi, deb, rpm
  --icon path/to/icon.icns \
  --app-version 1.0.0
```

> **注意**：打包时需要确保CEF二进制文件被正确包含在最终的安装包中

## 常见问题解答(FAQ)

### Q: 应用启动时出现"找不到CEF二进制文件"错误

**A**: 确保您已下载正确版本的CEF二进制文件，并将其放置在项目根目录的`runtimes`文件夹中。检查文件夹名称是否正确（注意大小写）。
注意下载jar包版本 110.0.25
国内可以通过 https://maven.aliyun.com/mvn/search 使用关键字 `jcef-natives` 搜索下载

### Q: 应用启动后显示白屏

**A**: 这通常是由于CEF渲染问题导致的。尝试在`Main.java`中添加以下CEF参数：

```java
builder.addJcefArgs("--disable-gpu");
builder.addJcefArgs("--disable-gpu-compositing");
builder.addJcefArgs("--disable-software-rasterizer");
builder.addJcefArgs("--in-process-gpu");
```

### Q: 如何调试JavaScript和Java之间的通信？

**A**: 在开发模式下，您可以使用浏览器的开发者工具进行调试。同时，在Java端添加详细的日志记录：

```java
logger.debug("收到来自JavaScript的请求: {}", request);
```

## 性能优化建议

1. **减少JavaScript和Java之间的通信频率** - 批量处理请求而不是频繁调用
2. **优化CEF设置** - 根据应用需求调整CEF参数
3. **使用Web Workers** - 对于复杂的JavaScript计算，使用Web Workers避免阻塞UI线程
4. **资源预加载** - 在应用启动时预加载常用资源
5. **内存管理** - 及时释放不再使用的资源，特别是大型对象

## 贡献指南

欢迎提交Issue和Pull Request来改进这个脚手架项目。请确保您的代码符合项目的编码规范和最佳实践。

1. Fork本仓库
2. 创建您的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交您的更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 打开一个Pull Request

## 许可证

本项目采用MIT许可证，详情请参阅LICENSE文件。

## 致谢

- [JCEF Maven](https://github.com/jcefmaven/jcefmaven) - 提供Java CEF的Maven集成
- [Chromium Embedded Framework](https://bitbucket