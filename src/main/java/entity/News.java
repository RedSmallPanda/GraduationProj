package entity;

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
}
