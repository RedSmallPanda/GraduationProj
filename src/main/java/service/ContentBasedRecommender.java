package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dao.NewsDao;
import dao.RecommendationDao;
import entity.News;
import entity.Recommendation;
import entity.User;
import utils.DateUtils;
import utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.MapUtils.sortDescend;

public class ContentBasedRecommender {
    /* 数据结构为
    { "娱乐":{0:{"1321231":60,"176352":50},{1:{"13212121":40,"112487":80}}}
      "时政":{0:{"1321231":60,"176352":50},{1:{"13212121":40,"112487":80}}}
    }
    即标签-->时间(0代表0:00-1:00以此类推)-->拥有此标签的新闻id和相关度
     */
    Map<String,Map<Integer,Map<String,Integer>>> tagNewsCollection = new HashMap<>();
    int pickHours = 2; //选取最近2小时的新闻
    int recommendNum = 20;//这里一次推荐20条新闻

    public void construct(){
        NewsDao newsDao = new NewsDao();
        List<News> news = newsDao.getNewsAfterTime(DateUtils.getCurrentHourTime()); //获取从最近整点开始的所有新闻
        constructSet(news);
    }

    public void recommend(User user){
        //获取和用户喜好最相似的新闻列表，然后将topN条推荐结果插入推荐表
        Map<String,Long> simNews = computeNewsSim(user);
        simNews = sortDescend(simNews);
        int count = 0;
        RecommendationDao recommendationDao = new RecommendationDao();
        for(String newsId : simNews.keySet()){
            if(count < 20){
                Recommendation rec = new Recommendation();
                rec.setUserId(user.getUserId());
                rec.setNewsId(newsId);
                rec.setRecNum(1);//1表示Content-based Recommendation
                rec.setRecTime(System.currentTimeMillis()/1000);
                recommendationDao.insertRecommendation(rec);
                count ++;
            }
        }

    }

    public Map<String,Long> computeNewsSim(User user){
        String interest = user.getInterest();
        JSONObject obj = JSON.parseObject(interest);
        Map<String,Object> interestMap = (Map<String,Object>)obj;
        List<Integer> neededHours = new ArrayList<>();
        int currentHour = DateUtils.getHour(System.currentTimeMillis());
        if(currentHour - (pickHours - 1) < 0){ //如果时间横跨了两天
            for(int i = 24-(pickHours-1);i < 24;i++){
                neededHours.add(i);
            }
            for(int i = 0;i <= currentHour;i++){
                neededHours.add(i);
            }
        }
        else{
            for(int i = currentHour - (pickHours - 1);i <= currentHour;i++){
                neededHours.add(i);
            }
        }
        Map<String,Long> res = new HashMap<>();

        for(Map.Entry<String,Object> userEntry : interestMap.entrySet()){
            String userInterest = userEntry.getKey();
            Integer interestValue = (Integer)userEntry.getValue();
            if(tagNewsCollection.containsKey(userInterest)){
                for(int i:neededHours){
                    Map<String,Integer> idMap = tagNewsCollection.get(userInterest).get(i); //获取到第三级Map
                    for(Map.Entry<String,Integer> newsFeature : idMap.entrySet()){
                        if(!res.containsKey(newsFeature.getKey())){
                            res.put(newsFeature.getKey(),new Long(newsFeature.getValue() * interestValue));
                        }
                        else{
                            Long tempValue = res.get(newsFeature.getKey());
                            tempValue += newsFeature.getValue() * interestValue;
                            res.put(newsFeature.getKey(),tempValue);
                        }
                    }
                }
            }
        }

        return res;
    }


    public void constructSet(List<News> news){
        for(News tempNews : news){
            //把新闻特征从String转为Map
            String feature = tempNews.getFeature();
            JSONObject obj = JSON.parseObject(feature);
            Map<String,Object> featureMap = (Map<String,Object>)obj;
            int newsHour = DateUtils.getHour(tempNews.getTimestamp());
            String newsId = tempNews.getNewsId();
            for(Map.Entry<String,Object> entry : featureMap.entrySet()){
                if(!tagNewsCollection.containsKey(entry.getKey())){
                    Map<Integer,Map<String,Integer>> timeFeatureMap = new HashMap<>();//第二级Map
                    for(int i = 0;i < 24;i++){
                        Map<String,Integer> idMap = new HashMap<>();//第三级Map
                        timeFeatureMap.put(i,idMap);
                    }
                    timeFeatureMap.get(newsHour).put(newsId,(Integer)entry.getValue());
                    tagNewsCollection.put(entry.getKey(),timeFeatureMap);
                }
                else{
                    tagNewsCollection.get(entry.getKey()).get(newsHour).put(newsId,(Integer)entry.getValue());
                }
            }

        }
    }
}
