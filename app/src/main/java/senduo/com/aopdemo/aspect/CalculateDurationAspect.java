package senduo.com.aopdemo.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import senduo.com.aopdemo.annotation.CalculateDuration;

/**
 * *****************************************************************
 * * 文件作者：ouyangshengduo
 * * 创建时间：2018/4/1
 * * 文件描述：
 * * 修改历史：2018/4/1 22:06*************************************
 **/

@Aspect
public class CalculateDurationAspect {

    //TODO 定义切面的规则
    //TODO 1.就是原来的应用中，哪些注释的地方放到当前切面进行处理
    //execution(注解名  注解用的地方)
    @Pointcut("execution(@senduo.com.aopdemo.annotation.CalculateDuration * *(..) )")
    public void methodAnonotatedWithCalculateDuration(){}

    //TODO 2.对进入切面的内容如何处理
    //advice
    //@Before() 在切入点之前运行
    //After()  在切入点之后运行
    //Around() 在切入点前后运行
    @Around("methodAnonotatedWithCalculateDuration()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable{

        //获取切面签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //根据签名 获取更多的信息
        String className = methodSignature.getDeclaringType().getSimpleName();//类名
        String methodName = methodSignature.getName();//方法名
        String funName = methodSignature.getMethod().getAnnotation(CalculateDuration.class).value();//功能名

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();//这里就是原先处理的逻辑
        long durarion = System.currentTimeMillis() - begin;
        Log.d("senduo",className + " " + methodName + " " + "本次" + funName + "耗时:" + durarion + "ms");

        return result;
    }
}
