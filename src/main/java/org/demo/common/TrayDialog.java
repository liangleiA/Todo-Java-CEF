package org.demo.common;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public class TrayDialog {

  private final JPopupMenu popupMenu;
  private final JDialog dialog;

  public TrayDialog(JPopupMenu popupMenu) {
    this.popupMenu = popupMenu;
    // 创建一个不可见的JDialog作为JPopupMenu的锚点
    this.dialog = new JDialog();
    dialog.setUndecorated(true);
    dialog.setSize(0, 0);

    // 确保点击菜单外部区域时菜单会自动隐藏
    popupMenu.addPopupMenuListener(new PopupMenuListener() {
      @Override
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        // 不需要处理
      }

      @Override
      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        hide();
      }

      @Override
      public void popupMenuCanceled(PopupMenuEvent e) {
        hide();
      }
    });

  }


  public void show() {
    // 使用MouseInfo获取鼠标的当前屏幕位置
    Point location = MouseInfo.getPointerInfo().getLocation();
    // 设置JDialog位置并显示
    dialog.setLocation(location.x, location.y);
    dialog.setVisible(true);
    // 显示JPopupMenu
    popupMenu.show(dialog, 0, 0);
  }

  public void hide(){
    dialog.setVisible(false);
  }


}
