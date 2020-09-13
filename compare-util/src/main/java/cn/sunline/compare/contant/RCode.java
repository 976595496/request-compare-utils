package cn.sunline.compare.contant;

/**
 * <h3>guli_parent</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-04-18 16:15
 */
public interface RCode {
    //成功
    Integer SUSSECC=20000;
    //失败
    Integer ERROR = 30000;
    //文件解析失败
    Integer ERROR_TXT_ANALYSIS = 50000;
    //回放请求异常
    Integer ERROR_PLAYBACK = 60000;
}
