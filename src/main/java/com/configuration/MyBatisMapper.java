package com.configuration;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * MyBatis 에서 지원하는 {@link org.apache.ibatis.annotations.Mapper} 는 Component 없어 IDE 어시스트 오류등 발생하여
 * 추가함
 */
@Component
@Documented
@Inherited
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface MyBatisMapper {

}
