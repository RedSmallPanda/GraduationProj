package service;

import com.alibaba.fastjson.JSON;
import entity.User;
import utils.JsonUtils;

import java.util.*;

public class KMeans {
    private List<User> dataArray;//待分类的原始值
    private int K = 3;//将要分成的类别个数
    private int maxClusterTimes = 500;//最大迭代次数
    private List<List<User>> clusterList;//聚类的结果
    private List<User> clusteringCenterT;//质心
    //TODO:填充领域列表
    private List<String> fieldList;//喜好领域列表 如:[ news, entertainment, game, tech, ...... ]

    public List<List<User>> clustering() {
        if (dataArray == null) {
            return null;
        }
        //初始K个点为数组中的前K个点
        int size = K > dataArray.size() ? dataArray.size() : K;
        List<User> centerT = new ArrayList<User>(size);
        //对数据进行打乱
        Collections.shuffle(dataArray);
        for (int i = 0; i < size; i++) {
            centerT.add(dataArray.get(i));
        }
        clustering(centerT, 0);
        return clusterList;
    }

    private void clustering(List<User> preCenter, int times) {
        if (preCenter == null || preCenter.size() < 2) {
            return;
        }
        //打乱质心的顺序
        Collections.shuffle(preCenter);
        List<List<User>> clusterList =  getListT(preCenter.size());
        for (User uu : this.dataArray) {
            //寻找最相似的质心
            int min = 0;
            double minScore = diffScore(uu, preCenter.get(0));
            for (int i = 1; i < preCenter.size(); i++) {
                if (minScore > diffScore(uu, preCenter.get(i))) {
                    minScore = diffScore(uu, preCenter.get(i));
                    min = i;
                }
            }
            clusterList.get(min).add(uu);
        }
        //计算本次聚类结果每个类别的质心
        List<User> nowCenter = new ArrayList<User> ();
        for (List<User> list : clusterList) {
            nowCenter.add(getCenterT(list));
        }
        //是否达到最大迭代次数
        if (times >= this.maxClusterTimes || preCenter.size() < this.K) {
            this.clusterList = clusterList;
            return;
        }
        this.clusteringCenterT = nowCenter;
        //TODO:
        //判断质心是否发生移动，如果没有移动，结束本次聚类，否则进行下一轮
        if (isCenterChange(preCenter, nowCenter)) {
            clear(clusterList);
            clustering(nowCenter, times + 1);
        } else {
            this.clusterList = clusterList;
        }
    }

    public double diffScore(User u1, User u2) {
        Map<String,Double> u1Map = JsonUtils.stringToMap(u1.getInterest());
        Map<String,Double> u2Map = JsonUtils.stringToMap(u2.getInterest());
        double distance = 0.0;
        Double value1 = 0.0;
        Double value2 = 0.0;
        Double diffSum = 0.0;
        for(String field : fieldList){
            if(u1Map.containsKey(field)){
                value1 = u1Map.get(field);
            }
            else{
                value1 = 0.0;
            }
            if(u2Map.containsKey(field)){
                value2 = u2Map.get(field);
            }
            else{
                value2 = 0.0;
            }
            diffSum = diffSum + (value1 - value2) * (value1 - value2);
        }
        distance = Math.sqrt(diffSum);
        return distance;
    }

    //初始化聚类结果
    private List<List<User>> getListT(int size) {
        List<List<User>> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new ArrayList<User>());
        }
        return list;
    }

    //计算新质心
    public User getCenterT(List<User> list) {
        Map<String,Double> sumInterest = new HashMap<>();
        for(String field : fieldList){
            sumInterest.put(field,0.0);
        }
        for(User u : list){
            Map<String,Double> tempInterest = JsonUtils.stringToMap(u.getInterest());
            for(Map.Entry<String,Double> singleInterest : tempInterest.entrySet()){
                double newValue = sumInterest.get(singleInterest.getKey()) + singleInterest.getValue();
                sumInterest.put(singleInterest.getKey(),newValue);
            }
        }
        int size = list.size();
        Map<String,Double> meanInterest = new HashMap<>();
        for(Map.Entry<String,Double> sum : sumInterest.entrySet()){
            meanInterest.put(sum.getKey(),sum.getValue()/size);
        }
        User res = new User();
        String str = JSON.toJSONString(meanInterest);
        res.setInterest(str);
        return res;
    }

    private boolean isCenterChange(List<User> preT, List<User> nowT){
        if (preT == null || nowT == null) {
            return false;
        }
        for (User u1 : preT) {
            boolean bol = true;
            for (User u2 : nowT) {
                if (equals(u1,u2)) {//t1在t2中有相等的，认为该质心未移动
                    bol = false;
                    break;
                }
            }
            //有一个质心发生移动，认为需要进行下一次计算
            if (bol) {
                return bol;
            }
        }
        return false;
    }

    public boolean equals(User u1,User u2){
        Map<String,Double> map1 = JsonUtils.stringToMap(u1.getInterest());
        Map<String,Double> map2 = JsonUtils.stringToMap(u2.getInterest());
        for(Map.Entry<String,Double> interest1 : map1.entrySet()){
            Double diff = interest1.getValue()-map2.get(interest1.getKey());
            //因为是Double类型，所以定义误差小于0.001即为相等
            if(Math.abs(diff)>0.001){
                return false;
            }
        }
        return true;
    }

    private void clear(List<List<User>> lists) {
        for (List<User> list : lists) {
            list.clear();
        }
        lists.clear();
    }

    public List<User> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<User> dataArray) {
        this.dataArray = dataArray;
    }

    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
    }

    public int getMaxClusterTimes() {
        return maxClusterTimes;
    }

    public void setMaxClusterTimes(int maxClusterTimes) {
        this.maxClusterTimes = maxClusterTimes;
    }

    public List<List<User>> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<List<User>> clusterList) {
        this.clusterList = clusterList;
    }

    public List<User> getClusteringCenterT() {
        return clusteringCenterT;
    }

    public void setClusteringCenterT(List<User> clusteringCenterT) {
        this.clusteringCenterT = clusteringCenterT;
    }
}
