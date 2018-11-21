package com.ideassoft.core.ajax;

public class AjaxResult {
    private int result;
    private String message;
    private Object data;

    public AjaxResult(int result, String message) {
        this.result = result;
        this.message = message;
    }

    public AjaxResult(int result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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
