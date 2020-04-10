package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dao.UserDao;
import entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterestRefresher {
    double refreshRate = 0.8;
    double deleteThreshold = 10;

    public void refresh(List<User> userList){
        // 遍历整个User列表，令每个兴趣领域乘以0.8的衰减系数，若兴趣值已经小于10，那么就直接删除此兴趣
        UserDao userDao = new UserDao();
        for(User user : userList){
            String interest = user.getInterest();
            JSONObject obj = JSON.parseObject(interest);
            Map<String,Object> interestMap = (Map<String,Object>)obj;
            Map<String,Integer> updatedMap = new HashMap<>();
            for(Map.Entry<String,Object> userInterest : interestMap.entrySet()){
                if((Integer)userInterest.getValue() > 10){
                    Double newValue = (Integer)userInterest.getValue() * refreshRate;
                    updatedMap.put(userInterest.getKey(),newValue.intValue());
                }
            }
            String updatedInterest = JSON.toJSONString(updatedMap);
            user.setInterest(updatedInterest);
            userDao.updateUser(user);
        }
    }
}
