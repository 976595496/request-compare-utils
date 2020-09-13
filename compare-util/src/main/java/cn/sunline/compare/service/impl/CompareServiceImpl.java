package cn.sunline.compare.service.impl;

import cn.sunline.compare.config.UnifyException;
import cn.sunline.compare.contant.GlobalProperties;
import cn.sunline.compare.contant.RCode;
import cn.sunline.compare.dao.ApiSetMapper;
import cn.sunline.compare.dao.CompareDiffMapper;
import cn.sunline.compare.dao.PlaybackResultMapper;
import cn.sunline.compare.dao.PositionFlagMapper;
import cn.sunline.compare.dto.compare.CompareResultDTO;
import cn.sunline.compare.entity.ApiSet;
import cn.sunline.compare.entity.CompareDiff;
import cn.sunline.compare.entity.PlaybackResult;
import cn.sunline.compare.entity.PositionFlag;
import cn.sunline.compare.service.CompareService;
import cn.sunline.compare.utils.jsonequals.InequalityDTO;
import cn.sunline.compare.utils.jsonequals.JsonCompareResult;
import cn.sunline.compare.utils.jsonequals.JsonRoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <h3>compare-util</h3>
 *
 * <p>比对服务层实现</p>
 *
 * @Author zcz
 * @Date 2020-07-03 11:12
 */
@Slf4j
@Service
public class CompareServiceImpl implements CompareService {

    @Autowired
    private PositionFlagMapper positionFlagMapper;

    @Autowired
    private PlaybackResultMapper playbackResultMapper;

    @Autowired
    private CompareDiffMapper compareDiffMapper;

    @Autowired
    private ApiSetMapper apiSetMapper;

    @Transactional
    @Override
    public CompareResultDTO compare() {
        CompareResultDTO resultDTO = new CompareResultDTO();

        List<CompareDiff> diffs = new ArrayList<>();
        // 根据回放业务存储的新旧系统回放记录结果   获取出来比对
        List<PlaybackResult> playbackResults = getPlaybackInfo();

        //获取忽略比对的交易名
        String noTransName = apiSetMapper.selectFirst().getNoTransName();
        List<String> transNames = new ArrayList<>();
        if (noTransName != null && !"".equals(noTransName)) {
             transNames = Arrays.asList(noTransName.split(","));
        }

        //新系统回放
        for (PlaybackResult playbackResult : playbackResults) {
            //判断交易是否不需要比对
            if (transNames.contains(playbackResult.getTransName())){
                continue;
            }
            //新系统json
            String newJson = playbackResult.getNewJson();
            //老系统json
            String oldJson = playbackResult.getOldJson();

            List<InequalityDTO> compareResult = null;
            if (GlobalProperties.COMPARE_CONTENT.equals(GlobalProperties.COMPARE_CONTENT_VALUE)) {
//            //比较json 数据
                log.info("比较json 数据");
                compareResult = compareNodeResult(newJson, oldJson);
            }else if (GlobalProperties.COMPARE_CONTENT.equals(GlobalProperties.COMPARE_CONTENT_KEY)) {
                //比较json 结构
                log.info("比较json 结构");
                compareResult = compareKeyResult(newJson, oldJson);
            }else {
                throw new UnifyException("比对模式配置异常, 请检查global.properties.compareMode 参数", RCode.ERROR_PLAYBACK);
            }

            //整理差异
            for (InequalityDTO d : compareResult) {
                CompareDiff diff = new CompareDiff();
                diff.setTransName(playbackResult.getTransName());
                diff.setFlowNum(playbackResult.getFlowNum());
                diff.setNewKey(d.getKey());
                diff.setNewValue(d.getJson1());
                diff.setOldKey(d.getKey());
                diff.setOldValue(d.getJson2());
                diff.setDiffType(d.getType());
                diffs.add(diff);
            }
        }
        //批量存储
        if (diffs.size()>0) {
            int count = compareDiffMapper.insertBatch(diffs);
        }
        resultDTO.setCompareDiffs(diffs);
        resultDTO.setDiffCount(diffs.size());
        return resultDTO;
    }

    /**
     * 获取未比对的回放结果
     * @return
     */
    private List<PlaybackResult> getPlaybackInfo() {
        PositionFlag positionFlag = positionFlagMapper.selectByFlag(GlobalProperties.COMPARE_FLG);
        Integer afterId;
        if (positionFlag == null){
            afterId = 0;
        }else {
            afterId = positionFlag.getPosition();
        }
        //根据之前回放的位置获取后面的请求
        List<PlaybackResult> playbackResults = playbackResultMapper.selectAfterId(afterId);

//        List<UnifyRequestAnalysis> unifyRequestAnalysisDTOS = TransferToUnifyRequestAnalysisDTOs(unifyRequestAnalyses);
        if (playbackResults.size()>0) {
            //记录当前回放位置
            if (positionFlag == null) {
                positionFlag = new PositionFlag();
                positionFlag.setFlag(GlobalProperties.COMPARE_FLG);
                positionFlag.setPosition(playbackResults.get(playbackResults.size()-1).getId()+1);
                positionFlagMapper.insert(positionFlag);
            } else {
                positionFlag.setPosition(playbackResults.get(playbackResults.size()-1).getId()+1);
                positionFlagMapper.updateByPrimaryKey(positionFlag);
            }
        }

        return playbackResults;
    }

    /**
     * 深度比对 json
     * @param newJson
     * @param oldJson
     * @return
     */
    private List<InequalityDTO> compareNodeResult(String newJson, String oldJson){

        JsonRoot newJsonRoot = JsonRoot.from(newJson);
        JsonRoot oldJsonRoot = JsonRoot.from(oldJson);
        Set<String> ignores = new HashSet<>();
        JsonCompareResult compareResult = newJsonRoot.compareNodeToWithIgnore(oldJsonRoot, ignores);
        List<InequalityDTO> inequalitys = compareResult.getInequalitys();

        return inequalitys;
    }

    /**
     * 比对 json 结构字段, 不包括值
     * @param newJson
     * @param oldJson
     * @return
     */
    private List<InequalityDTO> compareKeyResult(String newJson, String oldJson){
        JsonRoot newJsonRoot = JsonRoot.from(newJson);
        JsonRoot oldJsonRoot = JsonRoot.from(oldJson);
        Set<String> ignores = new HashSet<>();
        JsonCompareResult compareResult = newJsonRoot.compareKeyToWithIgnore(oldJsonRoot, ignores);
        List<InequalityDTO> inequalitys = compareResult.getInequalitys();


        return inequalitys;
    }






}
