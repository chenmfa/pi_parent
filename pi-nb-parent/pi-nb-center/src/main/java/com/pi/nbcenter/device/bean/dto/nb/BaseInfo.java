package com.pi.nbcenter.device.bean.dto.nb;

/**
 * @author lusj
 * @version V1.0
 * @Type BaseInfo
 * @Desc
 * @date 2018/6/25
 */
public class BaseInfo {


    /**
     *  // 0:成功，1:失败
     */
    private String returnCode;

    /**
     * 提示语
     */
    private String message;

    /**
     * 时间
     */
    private String date;

    /**
     * 返回实体
     */
    private Object result;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
