package dao;

import entity.News;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class NewsDao {
    // 指定全局配置文件
    private String resource = "mybatis-config.xml";
    // 读取配置文件
    private InputStream inputStream;
    // 构建sqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    public NewsDao(){
        try{
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<News> getNewsAfterTime(Long timestamp){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<News> news = sqlSession.selectList("NewsDao.queryNewsAllAfterTime", timestamp);
            return news;
        } finally {
            sqlSession.close();
        }
    }

    public News getNewsById(String id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            News news = sqlSession.selectOne("NewsDao.queryNewsById", id);
            return news;
        } finally {
            sqlSession.close();
        }
    }
}
