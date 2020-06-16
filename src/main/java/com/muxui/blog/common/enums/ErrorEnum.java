package com.muxui.blog.common.enums;

import com.muxui.blog.common.constant.ResultConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum ErrorEnum {

    SUCCESS(ResultConstants.OPERATION_SUCCESS, ResultConstants.SUCCESS_MESSAGE),
    ERROR(ResultConstants.OPERATION_FAIL, ResultConstants.ERROR_MESSAGE),

    DATA_NO_EXIST("00002", "该数据不存在"),
    PARAM_ERROR("00003", "参数错误"),
    LOGIN_DISABLE("00004", "账户已被禁用,请联系管理员解除限制"),
    LOGIN_ERROR("00005", "登录失败，用户名或密码错误"),
    ACCESS_NO_PRIVILEGE("00006", "不具备访问权限"),
    PARAM_INCORRECT("00007", "传入参数有误"),
    INVALID_TOKEN("00008", "token解析失败"),
    REGISTER_ADMIN("00009", "注册失败"),
    ACCOUNT_EXIST("00010", "账号已存在"),
    ACCOUNT_NOT_EXIST("00011", "用户不存在"),
    PASSWORD_ERROR("00012", "密码错误"),
    SYNC_POSTS_ERROR("00013", "同步文章失败"),
    UPDATE_PASSWORD_ERROR("00014", "密码修改失败"),
    FILE_TYPE_ERROR("00015", "文件类型错误"),
    IMPORT_FILE_ERROR("00016", "文件导入失败"),
    DATABASE_SQL_PARSE_ERROR("00017", "数据库解析异常"),
    ;

    private final static Map<String, ErrorEnum> errorEnumMap = new HashMap<>();

    static {
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            errorEnumMap.put(errorEnum.code, errorEnum);
        }
    }

    private final String code;
    private final String msg;


    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
     
    }
    public static String getMsgs( String code) {
        if (code.equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            return errorEnumMap.get(code).msg;
        } else {
            return errorEnumMap.get(code).msg;
        }
    }

    public static ErrorEnum getErrorEnumMap(String code) {
        return errorEnumMap.get(code);
    }
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
