package cn.comesaday.coe.core.basic.bean.result;

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
}
