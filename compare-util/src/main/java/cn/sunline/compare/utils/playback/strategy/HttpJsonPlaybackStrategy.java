package cn.sunline.compare.utils.playback.strategy;

import cn.sunline.compare.config.UnifyException;
import cn.sunline.compare.contant.RCode;
import cn.sunline.compare.entity.UnifyRequestAnalysis;
import cn.sunline.compare.utils.playback.PlaybackStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>Http发送 json 的请求回放策略</p>
 *
 * @Author zcz
 * @Date 2020-07-03 10:39
 */
@Component
public class HttpJsonPlaybackStrategy implements PlaybackStrategy<UnifyRequestAnalysis> {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @author zcz
     * @description 回访请求, 返回请求后的 json 字符串响应
     * @date 2020-07-07
     *
     * @method play
     * @param obj 请求参数
     * @return java.lang.String 返回 json 字符串
     */
    @Override
    public String play(UnifyRequestAnalysis obj, String url) {


        //header
        HttpHeaders headers = new HttpHeaders();
        try {
            Map map = objectMapper.readValue(obj.getHeaders(), Map.class);
            for (Object e : map.keySet()) {
                headers.set(e.toString(), map.get(e).toString());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new UnifyException("流水号 ["+obj.getFlowNum()+"] 请求头参数格式错误 ", RCode.ERROR_TXT_ANALYSIS);

        }


        String responseJson = null;
        try {



//        //请求方法
            HttpMethod method = null;
        if ("GET".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.GET;
        }else if ("HEAD".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.HEAD;
        }else if ("POST".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.POST;
        }else if ("PUT".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.PUT;
        }else if ("PATCH".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.PATCH;
        }else if ("DELETE".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.DELETE;
        }else if ("OPTIONS".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.OPTIONS;
        }else if ("TRACE".equals(obj.getMethod().toUpperCase())){
            method = HttpMethod.TRACE;
        }

            HttpEntity<String> entity = new HttpEntity<String>(obj.getBody(), headers);
//            SocketTimeoutException
            try {
                ResponseEntity<Object> responseEntity = restTemplate.exchange(url, method, entity, Object.class);

                if (responseEntity.getStatusCode().value() != 200){
                    throw new UnifyException(objectMapper.writeValueAsString(responseEntity), RCode.ERROR_PLAYBACK);
                }
                Object body = responseEntity.getBody();
                responseJson = objectMapper.writeValueAsString(body);
            }catch (ResourceAccessException e){
                e.printStackTrace();
                throw new UnifyException(e.getMessage(), RCode.ERROR_PLAYBACK);
            }

        }catch (JsonProcessingException e){

        }
        return responseJson ;
    }
}
