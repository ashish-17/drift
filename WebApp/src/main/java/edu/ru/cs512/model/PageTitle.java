package edu.ru.cs512.model;

public class PageTitle {
    
    private String id;
    private String text;
    
    public PageTitle(String title) {
        this.id = title;
        this.text = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
