package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dao.HistoryDao;
import dao.UserDao;
import entity.News;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class BrowseringNewsHandler {
    public void onBrowseringNews(User user, News news){
        HistoryDao historyDao = new HistoryDao();
        UserDao userDao = new UserDao();
        // 当用户点击一条新闻时，向新闻浏览记录表中插入一条新纪录
        historyDao.insertHistory(user.getUserId(),news.getNewsId());
        // 更新用户喜好，具体为把浏览新闻的标签即对应权重直接加入用户喜好
        String userInterest = user.getInterest();
        JSONObject userObj = JSON.parseObject(userInterest);
        Map<String,Object> originalInterestMap = (Map<String,Object>)userObj;
        Map<String,Integer> interestMap = new HashMap<>();
        for(Map.Entry<String,Object> originInterest : originalInterestMap.entrySet()){
            interestMap.put(originInterest.getKey(),(Integer)originInterest.getValue());
        }
        String newsFeature = news.getFeature();
        JSONObject newsObj = JSON.parseObject(newsFeature);
        Map<String,Object> featureMap = (Map<String,Object>)newsObj;
        for(Map.Entry<String,Object> feature : featureMap.entrySet()){
            if(interestMap.containsKey(feature.getKey())){
                Integer interestValue = interestMap.get(feature.getKey()) + (Integer)feature.getValue();
                interestMap.put(feature.getKey(),interestValue);
            }
            else{
                interestMap.put(feature.getKey(),(Integer)feature.getValue());
            }
        }
        String updatedInterest = JSON.toJSONString(interestMap);
        user.setInterest(updatedInterest);
        userDao.updateUser(user);

    }
}
