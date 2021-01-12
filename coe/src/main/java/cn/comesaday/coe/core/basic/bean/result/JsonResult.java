package cn.comesaday.coe.core.basic.bean.result;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

/**
 * <Descripe>
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 13:35
 */
public class JsonResult implements Serializable {

    private boolean success = true;

    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * <说明> 出现异常
     * @param code String 提示消息
     * @author ChenWei
     * @date 2020/8/10 14:58
     * @return void
     */
    public void setError(String code) {
        this.success = false;
        this.code = code;
    }

    public void setError(String code, String message) {
        this.success = false;
        this.message = message;
        this.code = code;
    }

    /**
     * <说明> 未出现异常情况
     * @param code String 提示消息
     * @param data Map<String, Object> 返回数据
     * @author ChenWei
     * @date 2020/8/10 14:59
     * @return void
     */
    public void setSuccess(String code, Map<String, Object> data) {
        this.success = true;
        this.code = code;
        this.data = data;
    }

    public void setSuccess(String code, String message) {
        this.success = true;
        this.code = code;
        this.message = message;
    }

    public void setSuccess(String code, String message, Map<String, Object> data) {
        this.success = true;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * <说明> 未出现异常情况
     * @param message String 提示消息
     * @author ChenWei
     * @date 2020/8/10 14:59
     * @return void
     */
    public void setSuccess(String message) {
        this.success = true;
        this.message = message;
    }

    @Override
    public String toString() {
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {

        }
        return json;
    }
}
