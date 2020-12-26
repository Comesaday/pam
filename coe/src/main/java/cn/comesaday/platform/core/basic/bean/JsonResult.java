package cn.comesaday.platform.core.basic.bean;

import java.util.Map;

/**
 * <Descripe>
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 13:35
 */
public class JsonResult {

    private boolean success;

    private String message;

    private Map<String, Object> data;

    public JsonResult() {

    }

    public JsonResult(boolean success) {
        this.success = success;
    }

    public JsonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonResult(boolean success, Map<String, Object> data) {
        this.success = success;
        this.data = data;
    }

    public JsonResult(boolean success, String message,
                      Map<String, Object> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * <说明> 出现异常
     * @param message String 提示消息
     * @author ChenWei
     * @date 2020/8/10 14:58
     * @return void
     */
    public void setAbnormal(String message) {
        this.success = false;
        this.message = message;
    }

    /**
     * <说明> 未出现异常情况
     * @param message String 提示消息
     * @param data Map<String, Object> 返回数据
     * @author ChenWei
     * @date 2020/8/10 14:59
     * @return void
     */
    public void setNolmal(String message, Map<String, Object> data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }
}
