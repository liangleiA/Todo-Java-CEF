package org.demo.common;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandler;
import org.cef.network.CefRequest;
import org.slf4j.Logger;

public class MyCefLoadHandler implements CefLoadHandler {
  private static final Logger logger = LogUtil.getLogger(MyCefLoadHandler.class);
  @Override
  public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
    logger.info("页面加载状态: {}", (isLoading ? "加载中" : "加载完成"));
  }

  @Override
  public void onLoadStart(CefBrowser cefBrowser, CefFrame cefFrame, CefRequest.TransitionType transitionType) {
    logger.info("开始加载页面: {}", cefFrame.getURL());
  }

  @Override
  public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {

  }

  @Override
  public void onLoadError(CefBrowser cefBrowser, CefFrame cefFrame, ErrorCode errorCode, String s, String s1) {

  }
}
