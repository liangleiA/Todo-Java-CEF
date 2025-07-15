interface Window {
  /**
   * CEF 查询接口
   * @param options 请求选项
   */
  cefQuery(options: {
    request: string;
    onSuccess?: (response: string) => void;
    onFailure?: (errorCode: number, errorMessage: string) => void;
  }): void;
}
