package org.demo.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Consumer;

public class AppConstant {

  static final String HTML_STATIC_PATH = "static";
  static final String LOG_NAME = "constant";
  static final String CHROME_RUNTIME_PATH = System.getProperty("user.dir") + File.separator + "runtimes/";


  /**
   * 获取CEF运行时目录
   */
  public static File getInstallRuntimeDir() {
    // 根据运行环境选择合适的CEF运行时目录
    String osName = System.getProperty("os.name").toLowerCase();
    String osArch = System.getProperty("os.arch").toLowerCase();
    String runtimePath = CHROME_RUNTIME_PATH;
    // 根据操作系统和架构选择合适的目录
    if (osName.contains("win")) {
      if (osArch.contains("arm") || osArch.contains("aarch64")) {
        runtimePath += "windows-arm64";
      } else if (osArch.contains("64") || osArch.contains("amd64")) {
        runtimePath += "windows-amd64";
      } else {
        runtimePath += "windows-x86";
      }
    } else if (osName.contains("mac") || osName.contains("darwin")) {
      if (osArch.contains("arm") || osArch.contains("aarch64")) {
        runtimePath += "macos-arm64";
      } else {
        runtimePath += "macos-amd64";
      }
    } else if (osName.contains("linux") || osName.contains("unix")) {
      if (osArch.contains("arm") || osArch.contains("aarch64")) {
        runtimePath += "linux-arm64";
      } else {
        runtimePath += "linux-amd64";
      }
    } else {
      // 默认使用amd64目录
      runtimePath += "windows-amd64";
    }
    LogUtil.info(LOG_NAME, "检测到操作系统: " + osName);
    LogUtil.info(LOG_NAME, "检测到系统架构: " + osArch);
    LogUtil.info(LOG_NAME, "使用CEF运行时目录: " + runtimePath);
    // 设置CEF运行时实例的目录
    File runtimeDir = new File(runtimePath);
    if (!runtimeDir.exists()) {
      LogUtil.info(LOG_NAME, "警告: 指定的运行时目录不存在: " + runtimeDir.getAbsolutePath());
      LogUtil.info(LOG_NAME, "尝试使用默认目录: runtimes/windows-amd64");
      runtimeDir = new File(System.getProperty("user.dir") + File.separator + "runtimes/windows-amd64");
    }
    return runtimeDir;
  }

  /**
   * 获取静态目录
   */
  public static String getStaticDir() {
    var staticDir = System.getProperty("user.dir") + File.separator + HTML_STATIC_PATH;
    // 确保静态目录存在
    File staticDirFile = new File(staticDir);
    if (!staticDirFile.exists()) {
      staticDirFile.mkdirs();
      LogUtil.info(LOG_NAME, "创建静态目录: " + staticDir);
    }
    return staticDir;
  }


  public static BufferedImage createTraySimpleBufferedImage() {
    // 创建一个简单的默认图标
    BufferedImage trayIconImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = trayIconImage.createGraphics();
    g2d.setColor(Color.BLUE);
    g2d.fillRect(0, 0, 16, 16);
    g2d.setColor(Color.WHITE);
    g2d.drawRect(0, 0, 15, 15);
    g2d.drawLine(0, 0, 15, 15);
    g2d.drawLine(15, 0, 0, 15);
    g2d.dispose();
    return trayIconImage;
  }


  public static JMenuItem createJMenuItem(String label, Consumer<ActionEvent> consumer) {
    final JMenuItem menuItem = new JMenuItem(label);
    menuItem.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
    menuItem.addActionListener(consumer::accept);
    return menuItem;
  }


}
