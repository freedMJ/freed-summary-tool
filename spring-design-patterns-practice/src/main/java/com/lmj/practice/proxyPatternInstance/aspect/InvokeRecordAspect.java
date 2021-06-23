package com.lmj.practice.proxyPatternInstance.aspect;

import com.lmj.practice.proxyPatternInstance.aspect.base.BaseMethodAspect;
import com.lmj.practice.proxyPatternInstance.handler.InvokeRecordHandler;
import com.lmj.practice.proxyPatternInstance.handler.base.MethodAdviceHandler;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Aspect
@Order(1)
@Component
public class InvokeRecordAspect extends BaseMethodAspect {


    /**
     * 指定切点（处理打上 InvokeRecordAnno 的方法）
     */
    @Override
    @Pointcut("@annotation(com.lmj.practice.proxyPatternInstance.annotation.InvokeRecordAnno)")
    protected void pointcut() { }


    /**
     * 指定该切面绑定的方法切面处理器为 InvokeRecordHandler
     */
    @Override
    protected Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType() {
        return InvokeRecordHandler.class;
    }




}
