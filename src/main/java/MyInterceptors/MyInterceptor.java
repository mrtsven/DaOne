package MyInterceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class MyInterceptor {

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        return context.proceed();
    }
}
