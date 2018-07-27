package com.urise.webapp.model.section;

import java.util.Date;

public class Conteiner {
    private String homePage;
    private Date startDate, endDate;
    private String title;
    private String text;

    public Conteiner(String homePage, Date startDate, Date endDate, String title, String text) {
        this.homePage = homePage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Conteiner{" +
                "homePage='" + homePage + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conteiner)) return false;

        Conteiner conteiner = (Conteiner) o;

        if (!homePage.equals(conteiner.homePage)) return false;
        if (!startDate.equals(conteiner.startDate)) return false;
        if (!endDate.equals(conteiner.endDate)) return false;
        if (!title.equals(conteiner.title)) return false;
        return text.equals(conteiner.text);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    public String getHomePage() {
        return homePage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
