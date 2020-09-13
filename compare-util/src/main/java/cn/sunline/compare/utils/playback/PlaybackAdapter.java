package cn.sunline.compare.utils.playback;

import cn.sunline.compare.entity.UnifyRequestAnalysis;

/**
 * <h3>compare-util</h3>
 *
 * <p>回放适配器: 泛型T 表示适配目标请求的对象类型, 将统一请求类型适配成目标请求参数类型</p>
 *
 * @Author zcz
 * @Date 2020-07-07 11:01
 */
public interface PlaybackAdapter<T> extends PlaybackStrategy<UnifyRequestAnalysis> {

    /**
     * @author zcz
     * @description 请求参数的适配, 将请求参数适配为相应适配目标的请求参数类型
     * @date 2020-07-07
     *
     * @method
     * @param obj 需要被适配的参数
     * @return java.lang.Object 返回适配目标的请求参数类型
     */
    T requestAdapt(UnifyRequestAnalysis obj);



    /**
     * @author zcz
     * @description 响应参数的适配, 将响应参数适配为统一的响应格式返回
     * @date 2020-07-07
     *
     * @method
     * @param obj 需要适配的响应参数
     * @return java.lang.Object 返回统一适配后的响应
     */
    String responseAdapt(Object obj);
}
