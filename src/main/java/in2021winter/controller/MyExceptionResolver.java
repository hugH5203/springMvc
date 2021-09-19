package in2021winter.controller;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**异常处理器，一旦发生异常，前端控制器就会调用异常处理器进行处理
 * @author HuangHai
 * @date 2021/2/21 13:49
 */
public class MyExceptionResolver implements HandlerExceptionResolver {


    /**
     * 用于处理异常
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e 这个异常参数就是程序中抛出来的异常
     * @return 返回值是一个ModelAndView，既可以存值，也可以跳到错误页面去
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        MyException myException=null;
        if (e instanceof MyException){  //如果抛出的异常是自定义异常，
            myException=(MyException) e;
        }else {
            myException=new MyException("程序出错");  //如果不是自定义异常，那也得是
        }
        ModelAndView modelAndView = new ModelAndView();  //就可以存错误信息与跳到错误页面去了
        modelAndView.addObject("errorMsg",myException.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
