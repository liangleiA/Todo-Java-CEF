package org.demo.common;


import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 开发者工具弹窗类
 */
public class DevToolsDialog extends JDialog {
  private final CefBrowser devTools_;

  public DevToolsDialog(JFrame owner, String title, CefBrowser browser){
    this(owner,title,browser,null);
  }

  public DevToolsDialog(JFrame owner,String title ,CefBrowser browser,Point inspectAt){
    super(owner,title,false);

    setLayout(new BorderLayout());
    // 使用Toolkit可以获得本机系统的屏幕的参数
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width/2,screenSize.height/2);
    // 居中显示
    setLocationRelativeTo(owner);
    setAlwaysOnTop(false);

    // 获取当前浏览器的开发工具的实例
    devTools_=browser.getDevTools(inspectAt);
    add(devTools_.getUIComponent());

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentHidden(ComponentEvent e) {
        dispose();
      }
    });
  }

  @Override
  public void dispose(){
    // 此方法为强制关闭浏览器窗口，会导致再次打开开发者工具出现空白页面
//        devTools_.close(true);
    super.dispose();
  }
}