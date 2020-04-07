package test;

import com.alibaba.fastjson.JSON;
import dao.NewsDao;
import entity.News;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NewsProcessorTest {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("/Users/kayaetsuden/Desktop/本科毕设/RecommenderSystem/mlc_dataset_part_aa");
        BufferedReader bf = new BufferedReader(fr);
        String str;
        int i = 0;
        // 按行读取字符串
        NewsDao newsDao = new NewsDao();
        str = bf.readLine();
        while ((str = bf.readLine()) != null && i<5000) {
            String[] strList = str.split("\\|,\\|");
            String[] classList = strList[1].split(",");
            //System.out.println(strList[0]+" "+strList[1]+" "+strList[2]);
            Set<String> feature = new HashSet<>();
            for(String ss:classList) {
                //System.out.println(ss);
                String[] bigFeature = ss.split("/");
                feature.add(bigFeature[0]);
            }
            //System.out.println(feature);
            News news = new News();
            news.setNewsId(strList[0]);
            news.setTitle(strList[2]);
            news.setTimestamp(System.currentTimeMillis()/1000);
            Map<String,Integer> featureTable = new HashMap<>();
            for(String sss:feature){
                featureTable.put(sss,50);
            }
            String ff = JSON.toJSONString(featureTable);
            System.out.println(ff);
            news.setFeature(ff);
            System.out.println(news);
            newsDao.setNews(news);

            i++;
        }
    }
}
