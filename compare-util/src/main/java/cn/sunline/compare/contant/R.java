package cn.sunline.compare.contant;

import lombok.Data;


/**
 * <h3>guli_parent</h3>
 *
 * <p>统一返回结果</p>
 *
 * @Author zcz
 * @Date 2020-04-18 16:18
 */

@Data
public class R<T> {

    /**
     * 是否成功
     */
    private Boolean Success;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data ;


    private R() { }

    public static R ok(){
        R result = new R();
        result.setSuccess(true);
        result.setCode(RCode.SUSSECC);
        result.setMessage("成功");
        return result;
    }


    public static R error(){
        R result = new R();
        result.setSuccess(false);
        result.setCode(RCode.ERROR_TXT_ANALYSIS);
        result.setMessage("失败");
        return result;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(T data){
        this.setData(data);
        return this;
    }

}
