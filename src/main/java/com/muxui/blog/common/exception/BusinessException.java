package com.muxui.blog.common.exception;


import com.muxui.blog.common.enums.ErrorEnum;


public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4214098630082643472L;
    // 异常编码
    private String code;

    public BusinessException(ErrorEnum errorEnum) {
        super(ErrorEnum.getMsgs(errorEnum.getCode()));
        this.code = errorEnum.getCode();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
