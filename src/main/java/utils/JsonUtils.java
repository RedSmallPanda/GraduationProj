package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    public static Map<String,Double> stringToMap(String str){
        JSONObject obj = JSON.parseObject(str);
        Map<String,Object> origMap = (Map<String,Object>)obj;
        Map<String,Double> interestMap = new HashMap<>();
        for(Map.Entry<String,Object> interest : origMap.entrySet()){
            interestMap.put(interest.getKey(),(Double)interest.getValue());
        }
        return interestMap;
    }
}
