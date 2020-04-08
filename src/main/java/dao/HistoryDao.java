package dao;

import entity.History;
import entity.News;
import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class HistoryDao {
    // 指定全局配置文件
    private String resource = "mybatis-config.xml";
    // 读取配置文件
    private InputStream inputStream;
    // 构建sqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    public HistoryDao(){
        try{
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public List<History> getHistoryAllAfterTime(Long timestamp){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<History> historys = sqlSession.selectList("HistoryDao.queryHistoryAllAfterTime", timestamp);
            return historys;
        } finally {
            sqlSession.close();
        }
    }

    public List<History> getNLineHistory(int lineNum){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<History> historys = sqlSession.selectList("HistoryDao.queryHistoryWithNLines", lineNum);
            return historys;
        } finally {
            sqlSession.close();
        }
    }

    public void insertHistory(String userId,String newsId){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            User user = sqlSession.selectOne("UserDao.queryUserById", userId);
            News news = sqlSession.selectOne("NewsDao.queryNewsById", newsId);
            History history = new History();
            history.setUserId(user.getUserId());
            history.setNewsId(news.getNewsId());
            history.setTimestamp(System.currentTimeMillis()/1000);
            history.setTitle(news.getTitle());
            history.setDay(0);
            int flag = sqlSession.insert("HistoryDao.insertHistory", history);
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }
    }

}
