package cn.sunline.compare.utils.playback;

/**
 * <h3>compare-util</h3>
 *
 * <p>回放工具接口: 泛型 T 是执行回放策略的发起请求的参数对象类</p>
 *
 * @Author zcz
 * @Date 2020-07-03 10:18
 */
public interface PlaybackStrategy<T> {

    /**
     * @author zcz
     * @description 回放发送请求, 返回 json 字符串
     * @date 2020-07-06
     *
     * @method play
     * @param obj 发送的请求
     * @return java.lang.String json 格式字符串
     */
    Object play(T obj, String url);
}
