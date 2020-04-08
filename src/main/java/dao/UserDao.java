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

public class UserDao {
    // 指定全局配置文件
    private String resource = "mybatis-config.xml";
    // 读取配置文件
    private InputStream inputStream;
    // 构建sqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    public UserDao(){
        try{
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public User getUserById(String id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            User user = sqlSession.selectOne("UserDao.queryUserById", id);
            return user;
        } finally {
            sqlSession.close();
        }
    }

    public void updateUser(User user){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int flag = sqlSession.update("UserDao.updateUser", user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public int countUser(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int count = sqlSession.selectOne("UserDao.countUser");
            return count;
        } finally {
            sqlSession.close();
        }
    }

}
