package cn.comesaday.coe.core.basic.bean.result;

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

    private String message;

    private Map<String, Object> data;

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
     * <说明> 返回错误
     * @param message 错误提示
     * @author ChenWei
     * @date 2021/3/8 17:15
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    public JsonResult setError(String message) {
        this.success = Boolean.FALSE;
        this.message = message;
        return this;
    }

    /**
     * <说明> 返回错误
     * @param message 错误提示
     * @param data 返回数据
     * @author ChenWei
     * @date 2021/3/8 17:15
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    public JsonResult setError(String message, Map<String, Object> data) {
        this.success = Boolean.FALSE;
        this.message = message;
        this.data = data;
        return this;
    }

    /**
     * <说明> 返回成功
     * @param message 成功提示
     * @author ChenWei
     * @date 2021/3/8 17:15
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    public JsonResult setSuccess(String message) {
        this.success = Boolean.TRUE;
        this.message = message;
        return this;
    }

    /**
     * <说明> 返回成功
     * @param message 成功提示
     * @param data 返回数据
     * @author ChenWei
     * @date 2021/3/8 17:16
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    public JsonResult setSuccess(String message, Map<String, Object> data) {
        this.success = Boolean.TRUE;
        this.message = message;
        this.data = data;
        return this;
    }
}
