package com.csl.anarres.annotation;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 19:11
 * @Description:
 */
/*
@Aspect
@Component
public class UserSelfOnlyAspect {
    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..))&& @annotation(com.csl.anarres.annotation.UserSelfOnly)")
    public void addUserId(){
    }//只是个函数签名，帮助记录的
    @Around("addUserId()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0){
            for(Object arg : args){
                if(arg.getClass().equals(ProgramDto.class) || arg.getClass().equals(ProgramEntity.class)){

                    Field createrId =  arg.getClass().getDeclaredField("createrId");
                    createrId.setAccessible(true);
                    //System.out.println(createrId);
                    createrId.set(arg,"123");


                    //System.out.println(arg.toString());
                }
            }
        }
        try {
            //该方法只能做参数校验，无法改变参数
            result = joinPoint.proceed();
        }catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }
}
*/