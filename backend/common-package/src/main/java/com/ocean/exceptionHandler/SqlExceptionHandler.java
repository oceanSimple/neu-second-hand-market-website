package com.ocean.exceptionHandler;

import com.ocean.common.R;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

// @ControllerAdvice
// @ResponseBody
public class SqlExceptionHandler {
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 处理重复异常
        // Duplicate entry 'ocean' for key 'idx_username'
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(0, msg);
        }
        return R.error(0, "未知错误");
    }
}
