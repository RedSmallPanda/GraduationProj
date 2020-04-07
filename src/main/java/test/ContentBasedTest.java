package test;

import dao.UserDao;
import entity.User;
import service.ContentBasedRecommender;

public class ContentBasedTest {
    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDao();
        ContentBasedRecommender contentBasedRecommender = new ContentBasedRecommender();
        User user = userDao.getUserById("1000637");
        System.out.println(user);
        contentBasedRecommender.construct();
        contentBasedRecommender.recommend(user);
    }
}
