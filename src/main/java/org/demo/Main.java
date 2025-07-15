package org.demo;

import com.google.gson.Gson;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefBuildInfo;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.demo.common.*;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
  // 创建日志记录器
  private static final Logger logger = LogUtil.getLogger(Main.class);
  // 应用程序名称常量
  private static final String APP_NAME = "脚手架";
  // 保存窗口、托盘图标和快捷键监听器的静态引用
  private static JFrame mainFrame;
  private static TrayIcon trayIcon;

  // 创建并注册全局快捷键监听器
  private static FrameVisibleManager frameVisibleManager;


  public static void main(String[] args) throws Exception {
    // 设置系统属性，确保正确的字符编码
    System.setProperty("file.encoding", "UTF-8");
    System.setProperty("sun.jnu.encoding", "UTF-8");

    CefAppBuilder builder = new CefAppBuilder();
    // window下不需要OSR
    boolean useOSR = false;
    builder.getCefSettings().windowless_rendering_enabled = useOSR;
    //解决无gpu报错
    builder.addJcefArgs("--no-sandbox");  // 禁用沙箱
    builder.addJcefArgs("--disable-gpu"); // 禁用GPU加速
    builder.addJcefArgs("--disable-gpu-rasterization");
    //解决白屏问题,使用cpu渲染
    builder.addJcefArgs("--disable-software-rasterizer");
    builder.addJcefArgs("--disable-gpu-compositing"); // 禁用GPU合成
    builder.addJcefArgs("--in-process-gpu");  // 强制GPU进程合并到主进程


    builder.setAppHandler(new MavenCefAppHandlerAdapter() {
      @Override
      public void stateHasChanged(org.cef.CefApp.CefAppState state) {
        // 关闭应用时退出jvm运行
        if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
      }
    });

    // 设置cef运行参数，这里为空
    builder.addJcefArgs(args);

    File runtimeDir = AppConstant.getInstallRuntimeDir();

    builder.setInstallDir(runtimeDir);
    // 由于是手动设置cef的runtimes，我们要跳过ins检查，防止版本不一致导致从镜像站下载
    builder.setSkipInstallation(true);

    // 全局的 CefApp 每个程序只能有一个，线程安全
    CefApp build = builder.build();
    // 显示一些版本信息
    CefBuildInfo buildInfo = CefBuildInfo.fromClasspath();
    logger.info("CEF Build Info: {}", new Gson().toJson(buildInfo));
    CefApp.CefVersion cefVersion = build.getVersion();
    logger.info("CEF Version: {}", cefVersion);

    // 创建一个浏览器客户端实例
    CefClient client = build.createClient();

    // 创建消息路由器
    CefMessageRouter msgRouter = CefMessageRouter.create();
    client.addMessageRouter(msgRouter);
    // 注册Java方法处理器
    msgRouter.addHandler(new MyCefMessageRouterHandler(), false);

    client.addKeyboardHandler(new KeyboardHandler(new JFrame()));
    client.addLoadHandler(new MyCefLoadHandler());
    boolean isTransparent = false;// 透明背景

    // 确定当前环境（开发环境或生产环境）
    boolean isDev = "dev".equals(AppEnum.ENV.getValue());
    // 根据环境选择URL
    String pageUrl;
    if (isDev) {
      // 开发环境：加载本地开发服务器
      pageUrl = "http://localhost:3000/index.html";
      logger.info("开发环境：加载开发服务器页面: {}", pageUrl);
    } else {
      // 生产环境：加载本地静态文件
      String staticDir = AppConstant.getStaticDir();
      pageUrl = "file:///" + staticDir.replace('\\', '/') + "/index.html";
      logger.info("生产环境：加载本地页面: {}", pageUrl);
    }

    // 创建一个浏览器实例，设置访问页面
    CefBrowser browser = client.createBrowser(pageUrl, useOSR, isTransparent);
    // 获取UI组件
    Component uiComponent = browser.getUIComponent();
    // 获得键盘焦点事件
    uiComponent.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        logger.debug("获得键盘焦点事件");
      }

      @Override
      public void focusLost(FocusEvent e) {
        logger.debug("失去键盘焦点事件");
      }
    });
    // 添加一个鼠标点击监听
    uiComponent.addMouseListener(new MouseAdapter() {
      int i = 0;
      @Override
      public void mouseClicked(MouseEvent e) {
        logger.debug("鼠标点击事件: {}", e);
        if (i % 2 == 0) // 执行JavaScript命令
          browser.executeJavaScript("alert('鼠标点击')", null, 1);
        i++;
      }
    });
    // 创建 JFrame UI 用于放入runtimes
    mainFrame = new JFrame(APP_NAME);
    mainFrame.getContentPane().add(uiComponent, BorderLayout.CENTER);
    mainFrame.setSize(800, 600);//大小
    mainFrame.setLocation(0, 0);//位置

    // 创建并注册全局快捷键监听器
    frameVisibleManager = new FrameVisibleManager(mainFrame);

    // 添加系统托盘功能
    if (SystemTray.isSupported()) {
      logger.info("系统支持托盘功能，正在创建托盘图标");

      // 创建系统托盘
      SystemTray systemTray = SystemTray.getSystemTray();
      BufferedImage trayIconImage = AppConstant.createTraySimpleBufferedImage();
      // 创建托盘图标对象
      trayIcon = new TrayIcon(trayIconImage, APP_NAME);
      trayIcon.setImageAutoSize(true);
      // 创建Swing托盘菜单（JPopupMenu）
      final JPopupMenu popupMenu = new JPopupMenu();
      popupMenu.setVisible(false);
      //添加显示
      popupMenu.add(
          AppConstant.createJMenuItem("显示/隐藏", e -> frameVisibleManager.toggleWindowVisibility())
      );
      // 添加分隔线
      popupMenu.addSeparator();
      popupMenu.add(AppConstant.createJMenuItem("退出", e -> {
        logger.info("通过托盘菜单退出应用");
        // 关闭应用时要释放资源
        CefApp.getInstance().dispose();
        mainFrame.dispose();
        systemTray.remove(trayIcon);
        System.exit(0); // 0正常退出，1非正常退出
      }));

      TrayDialog dialog = new TrayDialog(popupMenu);

      // 由于TrayIcon不直接支持JPopupMenu，我们需要添加鼠标事件监听器来显示JPopupMenu
      trayIcon.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
          if (e.isPopupTrigger()) {
            // 右键点击显示菜单
            dialog.show();
          } else if (e.getClickCount() == 2) {
            // 双击切换窗口显示状态
            frameVisibleManager.toggleWindowVisibility();
          }
        }

        @Override
        public void mousePressed(MouseEvent e) {
          if (e.isPopupTrigger()) {
            // 右键点击显示菜单
            dialog.show();
          }
        }
      });

      try {
        // 将托盘图标添加到系统托盘
        systemTray.add(trayIcon);
        logger.info("托盘图标添加成功");

        // 显示托盘通知
        trayIcon.displayMessage(APP_NAME, "应用已启动并添加到系统托盘", TrayIcon.MessageType.INFO);
      } catch (AWTException ex) {
        logger.error("无法添加托盘图标", ex);
      }

      // 修改窗口关闭行为，点击关闭按钮时最小化到托盘而不是退出
      mainFrame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          mainFrame.setVisible(false);
          trayIcon.displayMessage(APP_NAME, "应用已最小化到系统托盘", TrayIcon.MessageType.INFO);
        }
      });

      // 添加窗口状态监听，处理最小化事件
      mainFrame.addWindowStateListener(e -> {
        if (e.getNewState() == Frame.ICONIFIED) {
          // 窗口被最小化，隐藏窗口并显示托盘通知
          mainFrame.setVisible(false);
          trayIcon.displayMessage(APP_NAME, "应用已最小化到系统托盘", TrayIcon.MessageType.INFO);
        }
      });
    } else {
      logger.warn("系统不支持托盘功能");
      // 如果不支持系统托盘，使用默认的窗口关闭行为
      mainFrame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          // 关闭应用时要释放资源
          CefApp.getInstance().dispose();
          mainFrame.dispose();
          System.exit(0); // 0正常退出，1非正常退出
        }
      });
    }

    mainFrame.setVisible(true); // 显示窗口

    // 注册全局快捷键监听器
    frameVisibleManager.registerHook();
  }
}
