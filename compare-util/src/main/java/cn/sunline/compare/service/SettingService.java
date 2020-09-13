package cn.sunline.compare.service;

import cn.sunline.compare.vo.ApiSettingsVO;

/**
 * <h3>compare-util</h3>
 *
 * <p>设置服务层接口</p>
 *
 * @Author zcz
 * @Date 2020-07-10 09:57
 */
public interface SettingService {
    void save(ApiSettingsVO vo);

    ApiSettingsVO get();

    void clearTable(Short type);
}
