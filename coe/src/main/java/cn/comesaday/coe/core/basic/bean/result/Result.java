package cn.comesaday.coe.core.basic.bean.result;

import java.io.Serializable;

/**
 * <Description> Result
 * @author ChenWei
 * @CreateAt 2021-04-22 21:46
 */
public class Result {

    public static JsonResult set() {
        return new JsonResult();
    }

    public static JsonResult success() {
        return new JsonResult(Boolean.TRUE);
    }

    public static JsonResult success(String message) {
        return new JsonResult(Boolean.TRUE, message);
    }

    public static JsonResult success(String message, Object data) {
        return new JsonResult(Boolean.TRUE, message, data);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(Boolean.TRUE, data);
    }

    public static JsonResult fail() {
        return new JsonResult(Boolean.FALSE);
    }

    public static JsonResult fail(String message) {
        return new JsonResult(Boolean.FALSE, message);
    }

    public static JsonResult fail(String message, Object data) {
        return new JsonResult(Boolean.FALSE, message, data);
    }

    public static JsonResult fail(Object data) {
        return new JsonResult(Boolean.FALSE, data);
    }

    private static class JsonResult implements Serializable {

        private boolean success = true;

        private String message;

        private Object data;

        public JsonResult() {
        }

        public JsonResult(boolean success) {
            this.success = success;
        }

        public JsonResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public JsonResult(boolean success, Object data) {
            this.success = success;
            this.data = data;
        }

        public JsonResult(boolean success, String message, Object data) {
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

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
