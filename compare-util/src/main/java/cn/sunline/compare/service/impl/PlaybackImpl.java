package cn.sunline.compare.service.impl;

import cn.sunline.compare.config.UnifyException;
import cn.sunline.compare.contant.GlobalProperties;
import cn.sunline.compare.contant.R;
import cn.sunline.compare.contant.RCode;
import cn.sunline.compare.dao.ApiSetMapper;
import cn.sunline.compare.dao.PositionFlagMapper;
import cn.sunline.compare.dao.PlaybackResultMapper;
import cn.sunline.compare.dao.UnifyRequestAnalysisMapper;
import cn.sunline.compare.dto.playback.HttpReqResultDTO;
import cn.sunline.compare.entity.ApiSet;
import cn.sunline.compare.entity.UnifyRequestAnalysis;
import cn.sunline.compare.dto.playback.PlaybackDTO;
import cn.sunline.compare.dto.playback.RequestDTO;
import cn.sunline.compare.entity.PositionFlag;
import cn.sunline.compare.entity.PlaybackResult;
import cn.sunline.compare.service.PlaybackService;
import cn.sunline.compare.utils.playback.adapter.HttpJsonPlaybackAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>回放服务接口实现</p>
 *
 * @Author zcz
 * @Date 2020-07-03 10:15
 */
@Slf4j
@Service
public class PlaybackImpl implements PlaybackService {
    @Autowired
    private PositionFlagMapper playbackPositionMapper;

    @Autowired
    private UnifyRequestAnalysisMapper unifyRequestAnalysisMapper;

    @Autowired
    private PlaybackResultMapper playbackResultMapper;

    @Autowired
    private HttpJsonPlaybackAdapter httpJsonPlaybackAdapter;

    @Autowired
    private ApiSetMapper apiSetMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Transactional
    @Override
    public PlaybackDTO playback() {
        if (GlobalProperties.PLAYBACK_MODE.equals(GlobalProperties.PLAYBACK_MODE_DOUBLE)) {
            log.info("双系统回放");
            return doublePlayback();
        }else if (GlobalProperties.PLAYBACK_MODE.equals(GlobalProperties.PLAYBACK_MODE_SINGLE)){
            log.info("单系统回放");
            return singlePlayback();
        }
        throw new UnifyException("回放配置异常, 请检查global.properties.playbackMode 参数", RCode.ERROR_PLAYBACK);
    }

    public PlaybackDTO doublePlayback(){
        //数据库获取请求信息
        List<UnifyRequestAnalysis> objs = getRequestInfo();

        //返回结果
        PlaybackDTO playbackDTO = new PlaybackDTO();
        //统计数量
        Integer businessCount = objs.size();
        Integer failureCount = 0;
        List<RequestDTO> requestDtos = new ArrayList<>();
        ApiSet apiSet = apiSetMapper.selectFirst();
        if (apiSet == null) {
            throw new UnifyException("ApiHost 没有配置", RCode.ERROR_PLAYBACK);
        }


        if (objs.size() > 0) {
            //回放结果存储 后面做比对
            List<PlaybackResult> playbackResults = new ArrayList<>();
            for (UnifyRequestAnalysis obj : objs) {
                short successFlg = 1;
                PlaybackResult playbackResult = new PlaybackResult();

                //新系统回放
                String newUrl = obj.getProtocol() + "://" + apiSet.getNewHost() + ":" + apiSet.getNewPort() +
                        "/" + (obj.getPath().indexOf("/") == 0 ? obj.getPath().substring(1) : obj.getPath());
                HttpReqResultDTO newResult = httpRequest(newUrl, apiSet, obj, requestDtos, "new");


                //老系统回放 例如 jd
                String oldUrl = obj.getProtocol() + "://" + apiSet.getOldHost() + ":" + apiSet.getOldPort() +
                        "/" + (obj.getPath().indexOf("/") == 0 ? obj.getPath().substring(1) : obj.getPath());
                HttpReqResultDTO oldResult = httpRequest(newUrl, apiSet, obj, requestDtos, "old");
                System.out.println(newUrl + "\n" + oldUrl);

//                String oldJson = obj.getResponse();
                successFlg = (short) ((newResult.getSuccessFlg()==0 || oldResult.getSuccessFlg()==0)?0:1);
                if (successFlg<1) {
                    failureCount++;
                }


                playbackResult.setTransName(obj.getTransName());
                playbackResult.setFlowNum(obj.getFlowNum());
                playbackResult.setNewJson(newResult.getJson());
                playbackResult.setOldJson(oldResult.getJson());
                playbackResult.setSuccess(successFlg);
                playbackResults.add(playbackResult);

            }
            if (playbackResults.size() > 0) {
                int count = playbackResultMapper.insertBatch(playbackResults);
            }
        }
        playbackDTO.setRequestDtos(requestDtos);
        playbackDTO.setBusinessCount(businessCount);
        playbackDTO.setFailureCount(failureCount);
        playbackDTO.setSuccessCount(businessCount - failureCount);
        return playbackDTO;
    }


    public PlaybackDTO singlePlayback() {

        //数据库获取请求信息
        List<UnifyRequestAnalysis> objs = getRequestInfo();

        //返回结果
        PlaybackDTO playbackDTO = new PlaybackDTO();
        //统计数量
        Integer businessCount = objs.size();
        Integer failureCount = 0;
        List<RequestDTO> requestDtos = new ArrayList<>();
        ApiSet apiSet = apiSetMapper.selectFirst();
        if (apiSet == null) {
            throw new UnifyException("ApiHost 没有配置", RCode.ERROR_PLAYBACK);
        }


        if (objs.size() > 0) {
            //回放结果存储 后面做比对
            List<PlaybackResult> playbackResults = new ArrayList<>();
            for (UnifyRequestAnalysis obj : objs) {
                short successFlg = 1;
                PlaybackResult playbackResult = new PlaybackResult();

                //新系统回放
                String newUrl = obj.getProtocol() + "://" + apiSet.getNewHost() + ":" + apiSet.getNewPort() +
                        "/" + (obj.getPath().indexOf("/") == 0 ? obj.getPath().substring(1) : obj.getPath());
                HttpReqResultDTO newResult = httpRequest(newUrl, apiSet, obj, requestDtos, "new");


                //老系统回放 例如 jd
                String oldJson = obj.getResponse();


                successFlg = (short) (newResult.getSuccessFlg()==0?0:1);
                if (successFlg<1) {
                    failureCount++;
                }



                playbackResult.setTransName(obj.getTransName());
                playbackResult.setFlowNum(obj.getFlowNum());
                playbackResult.setNewJson(newResult.getJson());
                playbackResult.setOldJson(oldJson);
                playbackResult.setSuccess(successFlg);
                playbackResults.add(playbackResult);

            }
            if (playbackResults.size() > 0) {
                int count = playbackResultMapper.insertBatch(playbackResults);
            }
        }
        playbackDTO.setRequestDtos(requestDtos);
        playbackDTO.setBusinessCount(businessCount);
        playbackDTO.setFailureCount(failureCount);
        playbackDTO.setSuccessCount(businessCount - failureCount);
        return playbackDTO;
    }




    //发送请求 http
    private HttpReqResultDTO httpRequest(String newUrl, ApiSet apiSet, UnifyRequestAnalysis obj, List<RequestDTO> requestDtos, String flg) {
        HttpReqResultDTO dto = new HttpReqResultDTO();
        dto.setSuccessFlg((short) 1);
        if (apiSet.getNewPubParams() != null && !"".equals(apiSet.getNewPubParams())) {
            try {
                Map map1 = objectMapper.readValue(obj.getHeaders(), Map.class);
                Map map2 = objectMapper.readValue(apiSet.getNewPubParams(), Map.class);

                map2.putAll(map1);
                String headerStr = objectMapper.writeValueAsString(map2);
                obj.setHeaders(headerStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new UnifyException("流水号 [" + obj.getFlowNum() + "] 请求头参数格式错误 ", RCode.ERROR_PLAYBACK);
            }
        }
        //新系统回放
        String json = null;
        try {
            json = httpJsonPlaybackAdapter.play(obj, newUrl);
        } catch (UnifyException e) {
            dto.setSuccessFlg((short) 0);
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setFlowNum(obj.getFlowNum());
            requestDTO.setSource(flg);
            requestDTO.setPath(newUrl);
            requestDTO.setRequestMsg(obj.getBody());
            requestDTO.setResponseMsg(e.getMessage());
            requestDtos.add(requestDTO);
        }
        dto.setJson(json);
        return dto;
    }

    /**
     * 获取录入的请求信息
     * @return
     */
    private List<UnifyRequestAnalysis> getRequestInfo() {
        //获取之前回放的位置
        PositionFlag positionFlag = playbackPositionMapper.selectByFlag(GlobalProperties.PLAYBACK_FLG);
        Integer afterId;
        if (positionFlag == null){
             afterId = 0;
        }else {
            afterId = positionFlag.getPosition();
        }
        //根据之前回放的位置获取后面的请求
        List<UnifyRequestAnalysis> unifyRequestAnalyses = unifyRequestAnalysisMapper.selectAfterId(afterId);

//        List<UnifyRequestAnalysis> unifyRequestAnalysisDTOS = TransferToUnifyRequestAnalysisDTOs(unifyRequestAnalyses);
        if (unifyRequestAnalyses.size()>0) {
            //记录当前回放位置
            if (positionFlag == null) {
                positionFlag = new PositionFlag();
                positionFlag.setFlag(GlobalProperties.PLAYBACK_FLG);
                positionFlag.setPosition(unifyRequestAnalyses.get(unifyRequestAnalyses.size()-1).getId()+1);
                playbackPositionMapper.insert(positionFlag);
            } else {
                positionFlag.setPosition(unifyRequestAnalyses.get(unifyRequestAnalyses.size()-1).getId()+1);
                playbackPositionMapper.updateByPrimaryKey(positionFlag);
            }
        }


        return unifyRequestAnalyses;
    }

    private List<UnifyRequestAnalysis> TransferToUnifyRequestAnalysisDTOs(List<UnifyRequestAnalysis> unifyRequestAnalyses) {
        List<UnifyRequestAnalysis> dtos = new ArrayList<>();
        for ( UnifyRequestAnalysis source: unifyRequestAnalyses) {

            UnifyRequestAnalysis target = new UnifyRequestAnalysis();
            BeanUtils.copyProperties(source, target);

            dtos.add(target);
        }

        return dtos;

    }


}
