package entity;

import java.util.Objects;

public class History {
    private String userId;
    private String newsId;
    private String timestamp;
    private String title;
    private int day;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "History{" +
                "userId='" + userId + '\'' +
                ", newsId='" + newsId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", title='" + title + '\'' +
                ", day=" + day +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History history = (History) o;
        return getDay() == history.getDay() &&
                Objects.equals(getUserId(), history.getUserId()) &&
                Objects.equals(getNewsId(), history.getNewsId()) &&
                Objects.equals(getTimestamp(), history.getTimestamp()) &&
                Objects.equals(getTitle(), history.getTitle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserId(), getNewsId(), getTimestamp(), getTitle(), getDay());
    }
}
