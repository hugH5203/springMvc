package in2021winter.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HuangHai
 * @date 2021/2/20 21:58
 */

/**
 * 配置springMVC的拦截器，和web的过滤器不同的是，它只拦截controller方法
 * 它很像spring的环绕通知，且也是基于aop实现的
 * 必须实现HandlerInterceptor接口，即拦截器处理器
 */
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 拦截，在controller前执行，return true则放行，可以跳转或重定向
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o)throws Exception{
        System.out.println("在controller方法执行前拦截，类似于spring的前置通知");
        //可以跳转或重定向到其他页面
        return true;  //放行
    }

    /**
     * 在放行的情况下，会在controller的代码执行后执行，但在return的jsp页面跳转之前执行
     * 此时还可以跳转或重定向，但之后运行的controller里的return逻辑视图的跳转不会再执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("controller后，return .jsp前。类似spring的后置通知");
    }


    /**
     * 在return的jsp跳转之后执行，此时的http不能再进行跳转与重定向
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("最后执行，类似于spring的最终通知");
    }
}
