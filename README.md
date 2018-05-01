# Android-AOP
框架知识整理之Android AOP编程思想

# **开篇废话** #

真心非常感谢有这么一个五一假期，自己能够挪出来这么一大块的时间来好好学习，感觉无比幸福

近期，由于一个人的时间毕竟有限，工作上，学习上，生活上，都占用了太多时间，实在是没有抽出来时间好好整理自己的一些知识点，更糟糕的是，本来想到了不少的知识点想分享的，到今天想分享的时候，已经有点记不起哪些是当时想要分享的了，对此，我应该在一个日志工具中记录一下，免得出现现在这种情况。

近期花了一笔不少的钱报了一个在线进阶的班，跟着老师系统的把Android知识学习一遍，个人觉得还是挺值得的。因此，想着能把自己学到冰山一角整理一下，巩固一下自己的记忆，也供我们开发者一些参考。

废话就不多说了，今天就简单介绍一下AOP编程思想吧。。。

----------


# **技术详情** #

## **1. 什么是AOP** ##

      官方释义：面向切面编程，看到这个解释，其实对于没有接触过AOP的人来说，依然是一脸懵逼。
      我们可以联想一下我们自己使用最多的一种思想，就是面向对象编程。
      然后相较于面向对象，面向切面编程就是在多个功能中嵌入但又不是那个功能模块的一个主要功能，举个例子，大概是这样：
      A模块负责网络访问模块，B模块负责本地数据库模块，C模块负责日志打印模块，那么A和B模块里面都会嵌入
      C模块，也就是都需要打印一些日志，供我们调试或者记录，这个时候，我们就可以把C模块当做A模块和B模块的一个共同切面，
      而不是在A模块或者B模块的各个地方都写上的日志，而是集中在C模块中进行管理。

这么说可能还是不怎么好理解，那么我们就用代码的方式来比较说明吧

## **2. AOP的简易使用** ##

首先，我们实现一个简易的功能：计算某一段代码运行的时长，这样，我们可以知道到底是哪一个地方或者方法耗时比较多，可以有针对的对我们的app进行优化。

针对这个功能，我们应该在一段代码运行前，记录一个时间点，运行完，再用当前时间减去运行前的时间点，就会得出，这段代码运行的时长。

实现代码如下：

      /**
     * 模拟耗时操作：查询数据库
     * @param view
     */
    public void onFetchDatabase(View view){
        long begin = System.currentTimeMillis();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.i(TAG,"本次查询数据库耗时: " + duration + "ms");
    }

    /**
     * 模拟耗时操作：访问网络服务器
     * @param view
     */
    public void doNetAction(View view){
        long begin = System.currentTimeMillis();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.i(TAG,"本次访问网络服务器耗时: " + duration + "ms");
    }


    /**
     * 模拟耗时操作：保存本地文件
     * @param view
     */
    public void saveLocalFile(View view){
        long begin = System.currentTimeMillis();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.i(TAG,"本次保存本地文件耗时: " + duration + "ms");
    }

    /**
     * 模拟耗时操作：做其他操作
     * @param view
     */
    public void onDoOtherSomething(View view){
        long begin = System.currentTimeMillis();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.i(TAG,"本次操作耗时: " + duration + "ms");
    }

从以上代码，我们可以看出来，在每一个方法里面，都必须有一段相同的代码来计算耗时，当我们的功能模块很多很多的时候，这种写法就会很蛋疼了，到时候后期维护也不得了，这还只是单纯的一个耗时统计功能而已，如果还有其他类似的功能呢？

那么，下面我们使用AOP的思想来完成这个功能，其中的好处，自行体会。

涉及的知识点有：

      1.自定义注解的简易使用
      2.AspectJ的引入（可以理解为引入一个jar包），作为切面处理的一个工具类
 

首先创建一个自定义注解CalculateDuration ：

      @Target(ElementType.METHOD)
      @Retention(RetentionPolicy.RUNTIME)
      public @interface CalculateDuration {
          String value();
      }
    
然后引入AspecgJ编译器（一种与javac类似的编译器，可以不用深究，能用就行），具体使用，可以查看我的github源码中的app模块的gradle配置

接着就是实现切面的逻辑代码：

      
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
      / /Around() 在切入点前后运行
      @Around("methodAnonotatedWithCalculateDuration()")
      public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable{

          //获取切面签名
          MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
          //根据签名 获取更多的信息
          String className = methodSignature.getDeclaringType().getSimpleName();//类名
          String methodName = methodSignature.getName();//方法名
          String funName =       methodSignature.getMethod().getAnnotation(CalculateDuration.class).value();//功能名

          long begin = System.currentTimeMillis();
          Object result = joinPoint.proceed();//这里就是原先处理的逻辑
          long durarion = System.currentTimeMillis() - begin;
          Log.d("senduo",className + " " + methodName + " " + "本次" + funName + "耗时:" + durarion + "ms");

          return result;
      }
    }

通过以上切面的实现，在我们最开始的那段代码就变成一下的样子了：

    @CalculateDuration("查询数据库")
    public void onFetchDatabase(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

    @CalculateDuration("访问网络服务器")
    public void doNetAction(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

    @CalculateDuration("保存本地文件")
    public void saveLocalFile(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

    @CalculateDuration("做其他操作")
    public void onDoOtherSomething(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

这样，在这个地方，我们只需要关注自身的功能实现，而不需要插入那么多乱七八糟的代码

关于此项目的源代码，在文章最后，会提供github地址，欢迎star


# **干货总结** #

以上，是这一次关于AOP编程思想知识的一个简易整理，如果对于这一块有兴趣，可以多花点时间，好好去研究，我这边暂时也只能进行一个简单陈述。

此文章的作用有如下几点：

      1.认识一下AOP编程
      2.能够优化我们工作中的一些全局业务（比如性能检测，权限验证，资源释放，用户行为统计等）
      3.服务器那边可通过AOP思想进行数据挖掘，数据分析


以下是此demo的源码：


