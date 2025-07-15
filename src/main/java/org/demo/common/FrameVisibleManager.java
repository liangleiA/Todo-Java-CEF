package org.demo.common;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class FrameVisibleManager implements NativeKeyListener {
    // 创建日志记录器
    private static final Logger logger = LogUtil.getLogger(FrameVisibleManager.class);
    
    // 定义快捷键组合：Alt+M
    private static final int HOTKEY_MODIFIER = NativeKeyEvent.VC_ALT;
    private static final int HOTKEY_KEY = NativeKeyEvent.VC_M;
    
    // 跟踪修饰键状态
    private static boolean isAltPressed = false;
    
    // 保存窗口和托盘图标的引用
    private final JFrame mainFrame;

    /**
     * 构造函数，接收主窗口和托盘图标引用
     * 
     * @param mainFrame 主窗口引用
     */
    public FrameVisibleManager(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    /**
     * 注册全局键盘监听器
     */
    public void registerHook() {
        try {
            // 禁用JNativeHook库的日志输出
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            logger.setUseParentHandlers(false);
            
            // 注册本地钩子
            GlobalScreen.registerNativeHook();
            
            // 添加键盘监听器
            GlobalScreen.addNativeKeyListener(this);
            
            logger.info("全局快捷键监听器已注册，使用 Alt+M 切换窗口显示状态");
        } catch (NativeHookException ex) {
            logger.error("无法注册全局快捷键监听器", ex);
        }
    }
    
    /**
     * 注销全局键盘监听器
     */
    public void unregisterHook() {
        try {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.unregisterNativeHook();
            logger.info("全局快捷键监听器已注销");
        } catch (NativeHookException ex) {
            logger.error("无法注销全局快捷键监听器", ex);
        }
    }
    
    /**
     * 切换窗口的显示/隐藏状态
     * 公共方法，可以从外部调用
     */
    public void toggleWindowVisibility() {
        if (mainFrame != null) {
            if (mainFrame.isVisible()) {
                // 隐藏窗口
                mainFrame.setVisible(false);
                logger.info("通过快捷键隐藏窗口");
            } else {
                // 显示窗口并置顶
                mainFrame.setAlwaysOnTop(true); // 设置窗口始终置顶
                mainFrame.setVisible(true);
                mainFrame.setState(Frame.NORMAL); // 如果窗口被最小化，恢复它
                mainFrame.toFront(); // 将窗口置于前台
                // 短暂延迟后取消置顶，以避免窗口一直保持在最前面
                new Thread(() -> {
                    try {
                        Thread.sleep(200); // 延迟200毫秒
                        SwingUtilities.invokeLater(() -> mainFrame.setAlwaysOnTop(false));
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
                logger.info("通过快捷键显示窗口");
            }
        }
    }

    /**
     * 处理键盘按下事件
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // 检测Alt键
        if (e.getKeyCode() == HOTKEY_MODIFIER) {
            isAltPressed = true;
        }
        // 如果Alt键已按下，并且按下了M键，则切换窗口显示状态
        else if (isAltPressed && e.getKeyCode() == HOTKEY_KEY) {
            toggleWindowVisibility();
        }
    }

    /**
     * 处理键盘释放事件
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // 检测Alt键释放
        if (e.getKeyCode() == HOTKEY_MODIFIER) {
            isAltPressed = false;
        }
    }

    /**
     * 处理键盘输入事件（不需要实现）
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // 不需要处理
    }
}