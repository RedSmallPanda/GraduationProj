package dao;

import entity.News;
import entity.Recommendation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class RecommendationDao {
    // 指定全局配置文件
    private String resource = "mybatis-config.xml";
    // 读取配置文件
    private InputStream inputStream;
    // 构建sqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    public RecommendationDao(){
        try{
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Recommendation> getRecommendationByUserId(Long userId){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Recommendation> recommendation = sqlSession.selectList("RecommendationDao.queryRecommendationByUserId", userId);
            return recommendation;
        } finally {
            sqlSession.close();
        }
    }

    public void insertRecommendation(Recommendation rec){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int flag = sqlSession.insert("RecommendationDao.insertRecommendation",rec);
        } finally {
            sqlSession.close();
        }
    }
}
