package entity;

import java.util.Objects;

public class Recommendation {
    private Long id;
    private String userId;
    private String newsId;
    private Long recTime;
    private int recNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getRecTime() {
        return recTime;
    }

    public void setRecTime(Long recTime) {
        this.recTime = recTime;
    }

    public int getRecNum() {
        return recNum;
    }

    public void setRecNum(int recNum) {
        this.recNum = recNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recommendation)) return false;
        Recommendation that = (Recommendation) o;
        return getRecNum() == that.getRecNum() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getNewsId(), that.getNewsId()) &&
                Objects.equals(getRecTime(), that.getRecTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUserId(), getNewsId(), getRecTime(), getRecNum());
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", newsId='" + newsId + '\'' +
                ", recTime=" + recTime +
                ", recNum=" + recNum +
                '}';
    }
}
