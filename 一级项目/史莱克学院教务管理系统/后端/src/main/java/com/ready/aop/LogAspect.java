package com.ready.aop;

import com.ready.mapper.OperateLogMapper;
import com.ready.pojo.OperateLog;
import com.ready.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 环绕通知
    @Around("@annotation(com.ready.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 开始时间
        long begin = System.currentTimeMillis();

        // 2. 执行原始方法
        Object result = joinPoint.proceed();

        // 3. 结束时间，计算耗时
        long end = System.currentTimeMillis();
        long costTime = end - begin;

        // 4. 封装日志信息
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(CurrentHolder.getCurrentId());
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(result != null ? result.toString() : "void");
        olog.setCostTime(costTime);

        log.info("记录操作日志: {}", olog);
        // 5. 保存日志到数据库
        operateLogMapper.insert(olog);

        return result;
    }
}