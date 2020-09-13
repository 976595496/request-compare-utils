package cn.sunline.compare.service.impl;

import cn.sunline.compare.contant.GlobalProperties;
import cn.sunline.compare.dao.*;
import cn.sunline.compare.entity.ApiSet;
import cn.sunline.compare.entity.CompareDiff;
import cn.sunline.compare.entity.PositionFlag;
import cn.sunline.compare.service.SettingService;
import cn.sunline.compare.vo.ApiSettingsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h3>compare-util</h3>
 *
 * <p>设置服务层实现</p>
 *
 * @Author zcz
 * @Date 2020-07-10 09:58
 */
@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private ApiSetMapper apiSetMapper;

    @Autowired
    private UnifyRequestAnalysisMapper unifyRequestAnalysisMapper;

    @Autowired
    private PlaybackResultMapper playbackResultMapper;

    @Autowired
    private CompareDiffMapper compareDiffMapper;

    @Autowired
    private PositionFlagMapper positionFlagMapper;


    @Override
    public void save(ApiSettingsVO vo) {
        ApiSet apiSet = new ApiSet();

        String[] newApis = vo.getNewApi().split(":");
        apiSet.setNewHost(newApis[0]);
        apiSet.setNewPort(newApis[1]);
        apiSet.setNewPubParams(vo.getNewPubParams());
        String[] oldApis = vo.getOldApi().split(":");
        apiSet.setOldHost(oldApis[0]);
        apiSet.setOldPort(oldApis[1]);
        apiSet.setOldPubParam(vo.getOldPubParams());
        apiSet.setNoTransName(vo.getNoTransName());

        ApiSet apiSet1 = apiSetMapper.selectFirst();
        if (apiSet1 == null){
            apiSetMapper.insertSelective(apiSet);
        }else {
            apiSet.setId(apiSet1.getId());
            apiSetMapper.updateByPrimaryKeySelective(apiSet);
        }

    }

    @Override
    public ApiSettingsVO get() {
        ApiSet apiSet = apiSetMapper.selectFirst();
        ApiSettingsVO vo = new ApiSettingsVO();
        if (apiSet != null){
            vo.setNewApi(apiSet.getNewHost()+":"+apiSet.getNewPort());
            vo.setNewPubParams(apiSet.getNewPubParams());
            vo.setOldApi(apiSet.getOldHost()+":"+apiSet.getOldPort());
            vo.setOldPubParams(apiSet.getOldPubParam());
            vo.setNoTransName(apiSet.getNoTransName());
        }
        return vo;
    }

    @Override
    public void clearTable(Short type) {
        if (type == 1){
            unifyRequestAnalysisMapper.truncate();
            positionFlagMapper.updatePositionByFlag(0, GlobalProperties.PLAYBACK_FLG);
        }else if (type == 2){
            playbackResultMapper.truncate();
            compareDiffMapper.truncate();
            positionFlagMapper.updatePositionByFlag(0, GlobalProperties.COMPARE_FLG);
        }
    }
}
