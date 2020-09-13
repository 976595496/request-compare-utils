package cn.sunline.compare.config;

import cn.sunline.compare.contant.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <h3>compare-util</h3>
 *
 * <p>统一异常处理</p>
 *
 * @Author zcz
 * @Date 2020-07-08 14:38
 */
@RestControllerAdvice
public class UnifyExceptionHandler {

    /**
     * @author zcz
     * @description 统一异常处理
     * @date 2020-07-08
     *
     * @method analysisException
     * @param e 异常
     * @return cn.sunline.compare.contant.R
     */
    @ExceptionHandler(UnifyException.class)
    public R analysisException(UnifyException e){
        e.printStackTrace();
        return R.ok().message(e.getMessage()).code(e.getCode());
    }


    /**
     * @author zcz
     * @description 统一异常处理
     * @date 2020-07-08
     *
     * @method Exception
     * @param e 异常
     * @return cn.sunline.compare.contant.R
     */
    @ExceptionHandler(Exception.class)
    public R analysisException(Exception e){
        e.printStackTrace();
        return R.error().message(e.getClass() + e.getMessage());
    }
}
