package cn.edu.jit.exception;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常类
 * @author jitwxs
 * @date 2018/1/2 18:58
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();

        CustomException customException;
        if (e instanceof CustomException) {
            customException = (CustomException)e;
        } else if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
            // 用户名错误异常
            modelAndView.addObject("message", "用户名或密码错误");
            modelAndView.setViewName("global/error");
            return modelAndView;
        } else {
            customException = new CustomException("内部错误");
        }

        // 错误信息
        String message = customException.getMessage();

        // 错误信息传递和错误页面跳转
        modelAndView.addObject("message", message);
        modelAndView.setViewName("global/error");

        return modelAndView;
    }
}
