package com.ycao.mysite.schedule.aop;


import com.ycao.mysite.dao.AutotaskDao;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class ScheduleServiceAop {

    //为了记录执行时间 方便调试 如果不需要可以去掉
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    AutotaskDao autotaskDao;

    //定义一个切入点 我这里是从controller切入 不是从注解切入
    //详情看下方的 切入点表达式
    @Pointcut("execution(* com.ycao.mysite.schedule.Scheduler.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (startTime.get() == null) {
            startTime.set(System.currentTimeMillis());
        }
        //这句必须有 往下执行方法
        Object result = pjp.proceed();
        System.out.println("==================方法环绕后置start=====================>");

        Map<String,String> logMap = new HashMap<String,String>(){{
            put("cpn",pjp.getSignature().getDeclaringTypeName()+" - "+pjp.getSignature().getName());
            put("content","The result of execution is: "+String.valueOf(result)+"; It costs [" + (System.currentTimeMillis() - startTime.get())+"] ms");
        }};
        autotaskDao.addLog(logMap);

        System.out.println("==================方法环绕end======================>");
        return result;
    }
}
