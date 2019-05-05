package MyInterceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class MyInterceptor {

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        Logger logger = LoggerFactory.getLogger(context.getClass());
        logger.info("Method: " + context.getMethod().getName());
        return context.proceed();
    }
}
