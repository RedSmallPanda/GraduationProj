package service;

import entity.History;

import java.util.*;

import static utils.MapUtils.sortDescend;


public class UsercfRecommender {
    public void recommender(List<History> history, int userNum) {
        int[][] sparseMatrix = new int[userNum][userNum];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();//建立物品到用户的倒排表 eg: a A B
        Map<String, Set<String>> userItemCollection = new HashMap<>();//建立用户到物品的倒排表 eg: A a b
        Set<String> items = new HashSet<>();//辅助存储物品集合
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射
        int historyLen = history.size();
        int userCount = 0;
        for (int i = 0; i < historyLen; i++) {//依次处理每条用户历史数据
            //String[] user_item = scanner.nextLine().split(" ");
            History user_item = history.get(i);
            //每读到一条记录就为对应用户的浏览记录数加一
            if (userItemLength.containsKey(user_item.getUserId())) {
                int newCount = userItemLength.get(user_item.getUserId()) + 1;
                userItemLength.put(user_item.getUserId(), newCount);
            } else {
                userItemLength.put(user_item.getUserId(), 1);
            }
            if (!userID.containsKey(user_item.getUserId())) {
                userID.put(user_item.getUserId(), userCount);//用户ID与稀疏矩阵索引建立对应关系
                idUser.put(userCount, user_item.getUserId());
                userCount++;
            }

            //建立物品--用户倒排表
            if (items.contains(user_item.getNewsId())) {//如果已经包含对应的物品--用户映射，直接添加对应的用户
                itemUserCollection.get(user_item.getNewsId()).add(user_item.getUserId());
            } else {//否则创建对应物品--用户集合映射
                items.add(user_item.getNewsId());
                itemUserCollection.put(user_item.getNewsId(), new HashSet<String>());//创建物品--用户倒排关系
                itemUserCollection.get(user_item.getNewsId()).add(user_item.getUserId());
            }

            //建立用户--物品倒排表
            if (userItemCollection.containsKey(user_item.getUserId())) {//如果已经包含对应的用户--物品映射，直接添加对应的物品
                userItemCollection.get(user_item.getUserId()).add(user_item.getNewsId());
            } else {//否则创建对应用户--物品集合映射
                userItemCollection.put(user_item.getUserId(), new HashSet<String>());//创建用户--物品倒排关系
                userItemCollection.get(user_item.getUserId()).add(user_item.getNewsId());
            }
        }
        System.out.println(itemUserCollection.toString());

        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if (user_u.equals(user_v)) {
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        System.out.println(userItemLength.toString());
        String recommendUser = "5034018"; //这里先已一个固定的userID来测试；
        System.out.println(userID.get(recommendUser));
        double[] singleUserSimilarity = new double[userNum];//创建一个维数为用户个数的相似度向量
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0; j < sparseMatrix.length; j++) {
            if (j != recommendUserId && sparseMatrix[recommendUserId][j] > 0) {
                singleUserSimilarity[j] = sparseMatrix[recommendUserId][j] / Math.sqrt(userItemLength.get(idUser.get(recommendUserId)) * userItemLength.get(idUser.get(j)));
            }
        }
        Map<String,Double> positiveSimilarity = new HashMap<>();
        for(int j = 0; j < userNum; j++){
            if(singleUserSimilarity[j] > 0){
                positiveSimilarity.put(idUser.get(j),singleUserSimilarity[j]);
            }
        }

        positiveSimilarity = sortDescend(positiveSimilarity);
        int count = 1;
        Map<String,Double> itemCandidate = new HashMap<>();
        for(String simUser:positiveSimilarity.keySet()){
            if(count <= 20){ //这里固定考察前20个最相似用户
                for(String item : userItemCollection.get(simUser)){
                    if(!itemCandidate.containsKey(item)) {
                        Set<String> users = itemUserCollection.get(item);//得到购买当前物品的所有用户集合
                        if (!users.contains(recommendUser)) {//如果被推荐用户没有购买当前物品，则进行推荐度计算
                            double itemRecommendDegree = 0.0;
                            for (String user : users) {
                                //计算指定用户recommenderUser的物品推荐度
                                itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)] / Math.sqrt(userItemLength.get(recommendUser) * userItemLength.get(user));//推荐度计算
                            }
                            itemCandidate.put(item, itemRecommendDegree);
                        }
                    }
                }
                count++;
            }
        }
        //从候选物品中选出最相似的那些
        itemCandidate = sortDescend(itemCandidate);//按推荐度降序排列
        int recommendNum = 0;
        for(String item : itemCandidate.keySet()) { //这里固定选出用户可能感兴趣的前十个物品
            if(recommendNum <= 10) {
                System.out.println("The item " + item + " for " + recommendUser + "'s recommended degree:" + itemCandidate.get(item));
                recommendNum++;
            }
        }

//        //计算指定用户recommendUser的物品推荐度
//        for (String item : items) {//遍历每一件物品
//            Set<String> users = itemUserCollection.get(item);//得到购买当前物品的所有用户集合
//            if (!users.contains(recommendUser)) {//如果被推荐用户没有购买当前物品，则进行推荐度计算
//                double itemRecommendDegree = 0.0;
//                for (String user : users) {
//                    itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)] / Math.sqrt(userItemLength.get(recommendUser) * userItemLength.get(user));//推荐度计算
//                }
//                System.out.println("The item " + item + " for " + recommendUser + "'s recommended degree:" + itemRecommendDegree);
//            }
//        }

    }
}
