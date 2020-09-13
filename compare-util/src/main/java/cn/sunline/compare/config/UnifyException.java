package cn.sunline.compare.config;

import lombok.Data;

/**
 * <h3>compare-util</h3>
 *
 * <p>文件解析异常</p>
 *
 * @Author zcz
 * @Date 2020-07-08 14:41
 */
@Data
public class UnifyException extends RuntimeException {
    private Integer code;

    public UnifyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public UnifyException() {
        super();
    }
}
