package com.urise.webapp.model.section;

import java.util.Date;

public class Conteiner {
    private Date date1, date2;
    private String title;
    private  String text;

    public Conteiner(Date date1, Date date2, String title, String text) {
        this.date1 = date1;
        this.date2 = date2;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Conteiner{" +
                "date1=" + date1 +
                ", date2=" + date2 +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conteiner)) return false;

        Conteiner conteiner = (Conteiner) o;

        if (date1 != null ? !date1.equals(conteiner.date1) : conteiner.date1 != null) return false;
        if (date2 != null ? !date2.equals(conteiner.date2) : conteiner.date2 != null) return false;
        if (title != null ? !title.equals(conteiner.title) : conteiner.title != null) return false;
        return text != null ? text.equals(conteiner.text) : conteiner.text == null;
    }

    @Override
    public int hashCode() {
        int result = date1 != null ? date1.hashCode() : 0;
        result = 31 * result + (date2 != null ? date2.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    public Date getDate1() {
        return date1;
    }

    public Date getDate2() {
        return date2;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
