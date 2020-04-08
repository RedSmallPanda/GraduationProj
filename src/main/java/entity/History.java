package entity;

import java.util.Objects;

public class History {
    private Long historyId;
    private String userId;
    private String newsId;
    private Long timestamp;
    private String title;
    private int day;


    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History history = (History) o;
        return getDay() == history.getDay() &&
                Objects.equals(getHistoryId(), history.getHistoryId()) &&
                Objects.equals(getUserId(), history.getUserId()) &&
                Objects.equals(getNewsId(), history.getNewsId()) &&
                Objects.equals(getTimestamp(), history.getTimestamp()) &&
                Objects.equals(getTitle(), history.getTitle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getHistoryId(), getUserId(), getNewsId(), getTimestamp(), getTitle(), getDay());
    }

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", userId='" + userId + '\'' +
                ", newsId='" + newsId + '\'' +
                ", timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", day=" + day +
                '}';
    }
}
