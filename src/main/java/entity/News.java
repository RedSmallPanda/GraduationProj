package entity;

import java.util.Objects;

public class News {
    private String newsId;
    private String title;
    private Long timestamp;
    private String feature;


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return Objects.equals(getNewsId(), news.getNewsId()) &&
                Objects.equals(getTitle(), news.getTitle()) &&
                Objects.equals(getTimestamp(), news.getTimestamp()) &&
                Objects.equals(getFeature(), news.getFeature());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNewsId(), getTitle(), getTimestamp(), getFeature());
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", timestamp=" + timestamp +
                ", feature='" + feature + '\'' +
                '}';
    }
}
