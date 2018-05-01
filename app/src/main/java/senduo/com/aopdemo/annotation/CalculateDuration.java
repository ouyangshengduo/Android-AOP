package senduo.com.aopdemo.annotation;

/**
 * *****************************************************************
 * * 文件作者：ouyangshengduo
 * * 创建时间：2018/3/31
 * * 文件描述：
 * * 修改历史：2018/3/31 10:29*************************************
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CalculateDuration {
    String value();
}
