package entity;

import java.util.Objects;

public class News {
    private String newId;
    private String title;

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "News{" +
                "newId='" + newId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return Objects.equals(getNewId(), news.getNewId()) &&
                Objects.equals(getTitle(), news.getTitle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNewId(), getTitle());
    }
}
