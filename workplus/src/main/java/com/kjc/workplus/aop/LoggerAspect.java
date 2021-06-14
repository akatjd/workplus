package com.kjc.workplus.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.kjc.workplus..controller.*Controller.*(..)) or execution(* com.kjc.workplus..service.*Service.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Service") == true) {
			type = "Service ===> ";

		}
		
		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}

}
