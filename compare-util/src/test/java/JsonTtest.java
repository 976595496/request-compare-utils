import cn.sunline.compare.utils.jsonequals.JsonCompareResult;
import cn.sunline.compare.utils.jsonequals.JsonRoot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-04 10:48
 */
public class JsonTtest {
    @Test
    public void compareJsonStrIgnoringDiffInArray() throws JSONException {

        JsonRoot jsonRootA =JsonRoot.from("{\"responseNo\":\"1593744770821\",\"responseTime\":\"2020070310525 0\",\"channelCode\":\"CRPL_ZHONGYIN\",\"version\":\"1.0.0\",\"code\":\"00000\",\"msg\":\"成功\",\"responseData\":{\"applyNo\":\"1200603103909332969\",\"paymentNo\":\"21200603103909341514\",\"repaymentTotalAmount\":\"923780\",\"repaymentRealTotalAmount\":\"0\",\"repaymentPlanList\":[{\"paymentPeriod\":\"1\",\"interestStartTime\":\"20200603000000\",\"interestEndTime\":\"20200702000000\",\"repaymentDate\":\"20200703000000\",\"totalAmount\":\"76966\"},{\"paymentPeriod\":\"1\",\"interestStartTime\":\"20200603000000\",\"interestEndTime\":\"20200702000000\",\"repaymentDate\":\"20200703000000\",\"totalAmount\":\"76960\"}]}}");
        JsonRoot jsonRootB =JsonRoot.from("{\"responseNo\":\"1593744770821\",\"responseTime\":\"2020070310525 0\",\"channelCode\":\"CRPL_ZHONGYIN\",\"version\":\"1.0.0\",\"code\":\"00000\",\"msg\":\"成功\",\"responseData\":{\"applyNo\":\"1200603103909332969\",\"paymentNo\":\"21200603103909341514\",\"repaymentTotalAmount\":\"923780\",\"repaymentRealTotalAmount\":\"0\",\"repaymentPlanList\":\"abc\"}}");
        ObjectMapper objectMapper = new ObjectMapper();

        Set<String> ignores = new HashSet<>();
//        ignores.add("$.responseNo");
//        ignores.add("$.responseTime");
//        ignores.add("$.version");
//        ignores.add("$.code");

//        JsonCompareResult jsonCompareResult1 = jsonRootA.compareNodeToWithIgnore(jsonRootB, ignores);
//        try {
//            System.out.println(objectMapper.writeValueAsString(jsonCompareResult1.getInequalitys()));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        System.out.println("---------------------------------------------------------");

        JsonCompareResult jsonCompareResult2 = jsonRootA.compareKeyToWithIgnore(jsonRootB, ignores);

        try {
            System.out.println(objectMapper.writeValueAsString(jsonCompareResult2.getInequalitys()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}
