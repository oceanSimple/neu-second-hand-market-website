package com.ocean.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

//@Configuration
public class MyMetaObjectHandler  implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("gmtCreate", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("gmtCreate", Timestamp.valueOf(LocalDateTime.now()));
        metaObject.setValue("gmtModified", Timestamp.valueOf(LocalDateTime.now()));
    }
}
