package org.demo.common;

import org.cef.browser.CefBrowser;
import org.cef.handler.CefKeyboardHandlerAdapter;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class KeyboardHandler extends CefKeyboardHandlerAdapter {
  private final JFrame owner_;
  private DevToolsDialog devToolsDialog_ = null;

  public KeyboardHandler(JFrame owner_) {
    this.owner_ = owner_;
  }

  @Override
  public boolean onKeyEvent(CefBrowser cefBrowser, CefKeyEvent cefKeyEvent) {
    if (cefKeyEvent.type==CefKeyEvent.EventType.KEYEVENT_KEYUP){
      switch (cefKeyEvent.windows_key_code){
        // F12 开发者工具
        case KeyEvent.VK_F12:
          System.out.println("开发者工具 (F12)");
          devToolsShow(cefBrowser);
          break;
        // F5 刷新页面
        case KeyEvent.VK_F5:
          System.out.println("刷新页面 (F5)");
          cefBrowser.reload();
          break;
        default:
          return false;
      }
    }
    return true;
  }

  /**
   * 开发者工具显示或隐藏
   * @param cefBrowser 显示开发者工具的浏览器
   */
  private void devToolsShow(CefBrowser cefBrowser){
    if (devToolsDialog_ != null) {
      if (devToolsDialog_.isActive()) {
        devToolsDialog_.setVisible(false);
      } else {
        devToolsDialog_.setVisible(true);
      }
    } else {
      // 因为是开发者工具，不能影响内容页面的显示，所以单独新建一个窗体显示
      devToolsDialog_ = new DevToolsDialog(new JFrame(), "开发者工具", cefBrowser);
//            devToolsDialog_ = new DevToolsDialog(owner_, "开发者工具", cefBrowser);
      devToolsDialog_.setVisible(true);
    }
  }
}