package com.wn.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.wn.common.DatabaseType;
import com.wn.common.ReadOnlyConnection;
/**
 * 切面拦截器
 * @author wun
 *
 */
@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered{

	
	private static final Log logger = LogFactory.getLog(ReadOnlyConnectionInterceptor.class);

	  @Around("@annotation(readOnlyConnection)")
	  public Object proceed(ProceedingJoinPoint proceedingJoinPoint,ReadOnlyConnection readOnlyConnection) throws Throwable {
	    try {
	      logger.info("set database connection to read only");
	      DbContextHolder.setDbType(DatabaseType.SLAVE);
	      Object result = proceedingJoinPoint.proceed();
	      return result;
	    }finally {
	      DbContextHolder.clearDbType();
	      logger.info("restore database connection");
	    }
	  }


	  @Override
	  public int getOrder() {
	    return 0;
	  }
}
