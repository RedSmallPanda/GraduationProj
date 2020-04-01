package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dao.NewsDao;
import entity.News;

import java.util.Map;

public class FastjsonTest {
    public static void main(String[] args) throws Exception {
        NewsDao newsDao = new NewsDao();
        News news = newsDao.getNewsById("100487752");
        String feature = news.getFeature();
        JSONObject obj = JSON.parseObject(feature);
        Map<String,Object> featureMap = (Map<String,Object>)obj;
        for(Map.Entry<String,Object> entry : featureMap.entrySet()){
            System.out.println(entry.getKey());
            Integer aa = (Integer)entry.getValue();
            System.out.println(aa);
        }
    }
}
