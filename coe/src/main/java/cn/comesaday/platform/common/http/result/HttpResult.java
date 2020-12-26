package cn.comesaday.platform.common.http.result;

/**
 * <Descripe> io操作基础工具类
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 16:48
 */
public class HttpResult {

    private Integer code;

    private String body;

    public HttpResult() {
    }

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
