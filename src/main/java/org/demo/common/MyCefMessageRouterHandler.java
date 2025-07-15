package org.demo.common;


import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

public class MyCefMessageRouterHandler extends CefMessageRouterHandlerAdapter {

  @Override
  public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
    if ("hello".equals(request)) {
      // 返回数据给JS
      callback.success("Hello World from Java!");
      return true;
    }
    return false;
  }

}
