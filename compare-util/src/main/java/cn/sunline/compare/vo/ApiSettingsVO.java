package cn.sunline.compare.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <h3>compare-util</h3>
 *
 * <p>api 设置参数</p>
 *
 * @Author zcz
 * @Date 2020-07-03 09:29
 */
@Data
public class ApiSettingsVO {

    @NotNull(message = "新系统 api 不能为空")
    private String newApi;

    private String newPubParams;

    @NotNull(message = "旧系统 api 不能为空")
    private String oldApi;

    private String oldPubParams;

    private String noTransName;

}
