package test;

import dao.HistoryDao;
import dao.NewsDao;
import dao.UserDao;
import entity.History;
import entity.News;
import entity.Recommendation;
import entity.User;
import service.ContentBasedRecommender;
import service.UsercfRecommender;

import java.util.List;
import java.util.Map;

public class RecommendTest {
    public static void main(String[] args) throws Exception {
        HistoryDao historyDao = new HistoryDao();
        UserDao userDao = new UserDao();
        NewsDao newsDao = new NewsDao();
        List<History> history = historyDao.getNLineHistory(5000);
        int userNum = userDao.countUser();
        UsercfRecommender userCF = new UsercfRecommender();
        Map<String,Double> usercfRes = userCF.recommender(history,userNum,"7593272");
        ContentBasedRecommender contentBasedRecommender = new ContentBasedRecommender();
        contentBasedRecommender.construct();
        User user = userDao.getUserById("7593272");
        List<Recommendation> contentBasedRes = contentBasedRecommender.recommend(user);

        System.out.println("基于用户的协同过滤推荐：");
        for(Map.Entry<String,Double> aa:usercfRes.entrySet()){
            News news = newsDao.getNewsById(aa.getKey());
            System.out.println(news.getTitle()+" "+aa.getValue());
        }

        System.out.println("基于内容的推荐：");
        for(Recommendation rec: contentBasedRes){
            News conNews = newsDao.getNewsById(rec.getNewsId());
            System.out.println(conNews.getTitle());
        }
    }
}
