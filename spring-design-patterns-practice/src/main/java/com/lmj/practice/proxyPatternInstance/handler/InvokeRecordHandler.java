package com.lmj.practice.proxyPatternInstance.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.lmj.practice.proxyPatternInstance.annotation.InvokeRecordAnno;
import com.lmj.practice.proxyPatternInstance.handler.base.BaseMethodAdviceHandler;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Log4j2
public class InvokeRecordHandler extends BaseMethodAdviceHandler<Object> {

    /**
     * 记录方法出入参和调用时长
     */
    @Override
    public void onComplete(ProceedingJoinPoint point, long startTime, boolean permitted, boolean thrown, Object result) {
        String methodDesc = getMethodDesc(point);
        Object[] args = point.getArgs();
        long costTime = System.currentTimeMillis() - startTime;

        log.warn(StrUtil.format("\n{} 执行结束，耗时={}ms，入参={}, 出参={}",methodDesc, costTime, JSONUtil.formatJsonStr(args.toString()),
                JSONUtil.formatJsonStr(result.toString())));
    }

    @Override
    protected String getMethodDesc(ProceedingJoinPoint point) {
        Method targetMethod = getTargetMethod(point);
        // 获得方法上的 InvokeRecordAnno
        InvokeRecordAnno anno = targetMethod.getAnnotation(InvokeRecordAnno.class);
        String description = anno.value();

        // 如果没有指定方法说明，那么使用默认的方法说明
        if (StrUtil.hasBlank(description)) {
            description = super.getMethodDesc(point);
        }

        return description;
    }

}
