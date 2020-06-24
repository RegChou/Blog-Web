package com.muxui.blog.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.muxui.blog.common.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/24
 */
public class ThrowableUtils {
    /**
     * 校验参数正确,拼装字段名和值到错误信息
     * @param result
     */
    public static void checkParamArgument(BindingResult result) {
        if (result != null && result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            if(CollectionUtil.isNotEmpty(errors)){
                FieldError error = errors.get(0);
                String rejectedValue = Objects.toString(error.getRejectedValue(), "");
                String defMsg = error.getDefaultMessage();
                // 排除类上面的注解提示
                if(rejectedValue.contains("@")){
                    // 自己去确定错误字段
                    sb.append(defMsg);
                }else{
                    assert defMsg != null;
                    if( ":".contains(defMsg)){
                        sb.append(error.getField()).append(" ").append(defMsg);
                    }else{
                        sb.append(error.getField()).append(" ").append(defMsg).append(":").append(rejectedValue);
                    }
                }
            } else {
                String msg = result.getAllErrors().get(0).getDefaultMessage();
                sb.append(msg);
            }
            throw new BusinessException(sb.toString());
        }
    }
}
